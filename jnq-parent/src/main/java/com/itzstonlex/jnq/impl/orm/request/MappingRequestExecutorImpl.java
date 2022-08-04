package com.itzstonlex.jnq.impl.orm.request;

import com.itzstonlex.jnq.DataConnection;
import com.itzstonlex.jnq.exception.JnqException;
import com.itzstonlex.jnq.field.FieldType;
import com.itzstonlex.jnq.impl.content.SchemaContent;
import com.itzstonlex.jnq.impl.content.TableContent;
import com.itzstonlex.jnq.impl.field.IndexDataField;
import com.itzstonlex.jnq.impl.field.ValueDataField;
import com.itzstonlex.jnq.impl.orm.properties.MapObjectMapperProperties;
import com.itzstonlex.jnq.impl.orm.request.type.MappingRequestFinderImpl;
import com.itzstonlex.jnq.orm.ObjectMapper;
import com.itzstonlex.jnq.orm.ObjectMapperProperties;
import com.itzstonlex.jnq.orm.exception.JnqObjectMappingException;
import com.itzstonlex.jnq.orm.request.MappingRequest;
import com.itzstonlex.jnq.orm.request.MappingRequestExecutor;
import com.itzstonlex.jnq.orm.request.type.MappingRequestFinder;
import com.itzstonlex.jnq.request.query.session.RequestSessionAppender;
import com.itzstonlex.jnq.request.query.type.RequestCreateTable;
import com.itzstonlex.jnq.request.query.type.RequestFinder;
import com.itzstonlex.jnq.request.query.type.RequestInsert;
import com.itzstonlex.jnq.response.Response;
import com.itzstonlex.jnq.response.ResponseLine;
import com.itzstonlex.jnq.response.UpdateResponse;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MappingRequestExecutorImpl implements MappingRequestExecutor {

    DataConnection connection;
    String schema, table;

    ObjectMapper<Object> objectMapper;

    MappingRequestFinderImpl requestFinder;

    private void _checkTableValidation(ObjectMapperProperties properties)
    throws JnqException {

        SchemaContent schemaContent = connection.getSchemaContent(schema);

        // create schema if not exists.
        if (schemaContent.getTablesContents().isEmpty()) {
            schemaContent.create();
        }

        // create tables if not exists.
        TableContent tableContent = schemaContent.getTableContent(table);

        if (tableContent == null) {
            tableContent = new TableContent(table, schemaContent);

            RequestSessionAppender<IndexDataField, RequestCreateTable> createSession = tableContent.create()
                    .append(IndexDataField.createPrimaryNotNullAutoIncrement("id"));

            properties.foreach((name, value) -> {

                FieldType fieldType = FieldType.fromAttachment(value.getClass());
                createSession.append(IndexDataField.create(fieldType, name));
            });

            createSession.endpoint().compile().updateTransaction();
            connection.updateContents();
        }
    }

    private CompletableFuture<Integer> _insertObject(ObjectMapperProperties properties)
    throws JnqException {

        TableContent tableContent = connection.getTableContent(schema, table);

        RequestSessionAppender<ValueDataField, RequestInsert> insertSession = connection.createRequest(tableContent)
                .toFactory()

                .newInsert()
                .withUpdateDuplicatedKeys()

                .session();

        properties.foreach((name, value) -> {

            if (name.equalsIgnoreCase("id")) {
                return;
            }

            insertSession.append(ValueDataField.create(name, value));
        });

        UpdateResponse updateResponse = insertSession.endpoint().compile()
                .updateTransaction();

        return CompletableFuture.completedFuture(updateResponse.getGeneratedKey());
    }

    @Override
    public CompletableFuture<Integer> map(@NonNull Object object) throws JnqObjectMappingException {
        ObjectMapperProperties properties = new MapObjectMapperProperties();

        objectMapper.mapping(object, properties);

        _checkTableValidation(properties);
        return _insertObject(properties);
    }

    private void _checkTableValidation(Class<?> cls)
    throws JnqObjectMappingException {

        Map<String, Class<?>> map = connection.getObjectMappings().toMap(cls);

        SchemaContent schemaContent = connection.getSchemaContent(schema);

        // create schema if not exists.
        if (schemaContent.getTablesContents().isEmpty()) {
            schemaContent.create();
        }

        // create tables if not exists.
        TableContent tableContent = schemaContent.getTableContent(table);

        if (tableContent == null) {
            tableContent = new TableContent(table, schemaContent);

            RequestSessionAppender<IndexDataField, RequestCreateTable> createSession = tableContent.create()
                    .append(IndexDataField.createPrimaryNotNullAutoIncrement("id"));

            map.forEach((name, value) -> {

                FieldType fieldType = FieldType.fromAttachment(value);
                createSession.append(IndexDataField.create(fieldType, name));
            });

            createSession.endpoint().compile().updateTransaction();
            connection.updateContents();
        }
    }

    private Response _fetchAllResponse()
    throws JnqException {

        TableContent tableContent = connection.getTableContent(schema, table);

        RequestFinder newRequestFinder = connection.createRequest(tableContent)
                .toFactory()
                .newFinder();

        if (requestFinder.limit() > 0) {
            newRequestFinder.withLimit(requestFinder.limit());
        }

        requestFinder.includes().forEach(mappingDataField ->
                newRequestFinder.sessionFilter().and(mappingDataField.operator(), mappingDataField));

        return newRequestFinder.compile().fetchTransaction();
    }

    @SuppressWarnings("unchecked")
    @Override
    public @NonNull <T> LinkedList<T> fetchAll(@NonNull Class<T> cls) throws JnqObjectMappingException {
        if (requestFinder == null) {
            throw new JnqObjectMappingException("this request is not instance of Finder");
        }

        _checkTableValidation(cls);

        LinkedList<T> linkedList = new LinkedList<>();
        Response response = _fetchAllResponse();

        for (ResponseLine responseLine : response) {

            ObjectMapperProperties properties = new MapObjectMapperProperties();
            responseLine.getLabels().forEach(label -> properties.set(label.toLowerCase(), responseLine.getObject(label).orElse(null)));

            linkedList.add((T) objectMapper.fetch((Class<Object>) cls, properties));
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
