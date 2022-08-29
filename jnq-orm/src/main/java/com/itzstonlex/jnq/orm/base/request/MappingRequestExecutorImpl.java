package com.itzstonlex.jnq.orm.base.request;

import com.itzstonlex.jnq.content.Response;
import com.itzstonlex.jnq.content.ResponseLine;
import com.itzstonlex.jnq.content.UpdateResponse;
import com.itzstonlex.jnq.content.field.FieldType;
import com.itzstonlex.jnq.content.field.type.EntryField;
import com.itzstonlex.jnq.content.field.type.IndexField;
import com.itzstonlex.jnq.content.request.session.RequestSessionCollection;
import com.itzstonlex.jnq.content.request.session.RequestSessionCondition;
import com.itzstonlex.jnq.content.request.type.RequestCreateTable;
import com.itzstonlex.jnq.content.request.type.RequestFinder;
import com.itzstonlex.jnq.content.request.type.RequestInsert;
import com.itzstonlex.jnq.content.type.SchemaContent;
import com.itzstonlex.jnq.content.type.TableContent;
import com.itzstonlex.jnq.orm.ObjectMapper;
import com.itzstonlex.jnq.orm.ObjectMapperProperties;
import com.itzstonlex.jnq.orm.annotation.MappingID;
import com.itzstonlex.jnq.orm.annotation.MappingPrimary;
import com.itzstonlex.jnq.orm.base.request.type.MappingRequestFinderImpl;
import com.itzstonlex.jnq.orm.data.ObjectMappingService;
import com.itzstonlex.jnq.orm.exception.JnqObjectMappingException;
import com.itzstonlex.jnq.orm.request.MappingRequestExecutor;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MappingRequestExecutorImpl implements MappingRequestExecutor {

    SchemaContent schemaContent;
    String table;

    @NonFinal
    TableContent tableContent;

    ObjectMapper<Object> objectMapper;

    ObjectMappingService ormService;
    MappingRequestFinderImpl requestFinder;

    private String _getIdentifierName(ObjectMapperProperties properties) {
        return properties.peek(MappingID.PROPERTY_KEY_NAME, () -> MappingID.DEFAULT_COLUMN_NAME);
    }

    private boolean _hasIdentifier(ObjectMapperProperties properties) {
        return properties.peek(MappingID.PROPERTY_KEY_NAME) != null;
    }

    private RequestSessionCollection<IndexField, RequestCreateTable> _getTableCreateSession() {

        // create schema if not exists.
        if (schemaContent.getActiveTables().isEmpty()) {
            schemaContent.executeCreate();
        }

        // create tables if not exists.
        tableContent = schemaContent.getTableByName(table);

        if (tableContent == null) {
            tableContent = schemaContent.newTableInstance(table);
            return tableContent.newCreateSession();
        }

        return null;
    }

    private CompletableFuture<Integer> _insertObject(ObjectMapperProperties properties) {
        RequestSessionCollection<EntryField, RequestInsert> insertSession = tableContent.createRequest()
                .toFactory()

                .newInsert()
                .checkAvailability()

                .beginCollection();

        properties.foreach((name, value) -> {

            if (!name.equalsIgnoreCase(MappingID.PROPERTY_KEY_NAME) && !name.equalsIgnoreCase(_getIdentifierName(properties))) {
                insertSession.add(EntryField.create(name, value));
            }
        });

        UpdateResponse updateResponse = insertSession.endpoint()
                .compile()
                .updateTransaction();

        return CompletableFuture.completedFuture(updateResponse.getGeneratedKey());
    }

    private Response _fetchAllResponse() {
        RequestFinder parentRequestFinder = tableContent.createRequest()
                .toFactory()
                .newFinder();

        if (requestFinder.limit() > 0) {
            parentRequestFinder.markLimit(requestFinder.limit());
        }

        RequestSessionCondition<RequestFinder> condition = parentRequestFinder.beginCondition();

        requestFinder.entryFieldsAnd().forEach((entryField, operator) -> condition.and(operator, entryField));
        requestFinder.entryFieldsOr().forEach((entryField, operator) -> condition.or(operator, entryField));

        return condition.endpoint().compile().fetchTransaction();
    }

    @Override
    public CompletableFuture<Integer> save(@NonNull Object object) throws JnqObjectMappingException {
        ObjectMapperProperties properties = ormService.createProperties();

        objectMapper.serialize(object, properties);

        RequestSessionCollection<IndexField, RequestCreateTable> createSession = _getTableCreateSession();

        if (createSession != null) {
            Set<String> primaryFieldsNamesSet = properties.poll(MappingPrimary.PROPERTY_KEY_NAME);

            String id = properties.poll(MappingID.PROPERTY_KEY_NAME);

            createSession.add(IndexField.createPrimaryNotNullAutoIncrement(id));
            properties.remove(id);

            properties.foreach((name, value) -> {

                FieldType fieldType = FieldType.fromAttachment(value.getClass());
                IndexField indexField = IndexField.create(fieldType, name);

                if (primaryFieldsNamesSet.contains(name.toLowerCase())) {
                    indexField.index(IndexField.IndexType.PRIMARY);
                }

                createSession.add(indexField);
            });

            createSession.endpoint().compile().updateTransaction();
            schemaContent.updateTablesData();
        }

        return _insertObject(properties);
    }

    @SuppressWarnings("unchecked")
    @Override
    public @NonNull <T> LinkedList<T> fetchAll(@NonNull Class<T> cls) throws JnqObjectMappingException {
        if (requestFinder == null) {
            throw new JnqObjectMappingException("this request is not instance of Finder");
        }

        RequestSessionCollection<IndexField, RequestCreateTable> createSession = _getTableCreateSession();

        if (createSession != null) {
            Map<String, Class<?>> map = requestFinder.getObjectMappingService().toMap(cls);

            map.forEach((name, value) -> {

                FieldType fieldType = FieldType.fromAttachment(value);
                createSession.add(IndexField.create(fieldType, name));
            });

            createSession.endpoint().compile().updateTransaction();
            schemaContent.updateTablesData();
        }

        LinkedList<T> linkedList = new LinkedList<>();
        Response response = _fetchAllResponse();

        ObjectMapperProperties properties = ormService.createProperties();

        for (ResponseLine responseLine : response) {

            for (String label : responseLine.getLabels()) {
                properties.set(label, responseLine.getObject(label).orElse(null));
            }

            linkedList.add((T) objectMapper.deserialize((Class<Object>) cls, properties));
            properties.removeAll();
        }

        return linkedList;
    }

    @Override
    public <T> @NonNull T fetchFirst(@NonNull Class<T> cls) throws JnqObjectMappingException {
        return fetchAll(cls).getFirst();
    }

    @Override
    public <T> @NonNull T fetchLast(@NonNull Class<T> cls) throws JnqObjectMappingException {
        return fetchAll(cls).getLast();
    }
}
