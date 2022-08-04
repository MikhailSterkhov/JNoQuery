package com.itzstonlex.jnq.sql;

import com.itzstonlex.jnq.DataConnection;
import com.itzstonlex.jnq.content.DataContent;
import com.itzstonlex.jnq.exception.JnqException;
import com.itzstonlex.jnq.impl.content.SchemaContent;
import com.itzstonlex.jnq.impl.content.TableContent;
import com.itzstonlex.jnq.impl.field.MappingDataField;
import com.itzstonlex.jnq.impl.orm.ObjectMappingServiceImpl;
import com.itzstonlex.jnq.orm.ObjectMappingService;
import com.itzstonlex.jnq.request.Request;
import com.itzstonlex.jnq.response.Response;
import com.itzstonlex.jnq.response.ResponseLine;
import com.itzstonlex.jnq.sql.response.SQLResponse;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;

import java.sql.*;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@FieldDefaults(level = AccessLevel.PROTECTED, makeFinal = true)
public class SQLConnection implements DataConnection {

    private static final String SHOW_DATABASES_QUERY = "SHOW SCHEMAS;";
    private static final String SHOW_TABLES_QUERY = "SHOW TABLES ";

    private static final String TABLE_NAME_FORMAT = "%s.%s";

    @Getter
    SQLConnectionMeta meta;

    @Getter
    Connection sqlConnection;

    ObjectMappingService<MappingDataField> objectMappingService;

    Map<String, SchemaContent> schemaByNamesMap = new ConcurrentHashMap<>();
    Map<String, TableContent> tableByNameMap = new ConcurrentHashMap<>();

    public SQLConnection(String driverCls, @NonNull Connection sqlConnection) throws JnqException {
        try {
            if (driverCls != null) {
                Class.forName(driverCls);
            }

            this.sqlConnection = sqlConnection;

            this.objectMappingService = new ObjectMappingServiceImpl(this);
            this.meta = new SQLConnectionMeta(sqlConnection.getMetaData());

            this.updateContents();
        }
        catch (Exception exception) {
            throw new JnqException("init", exception);
        }
    }

    public SQLConnection(@NonNull Connection sqlConnection) throws JnqException {
        this(null, sqlConnection);
    }

    public SQLConnection(String driverCls, @NonNull String url, @NonNull String username, @NonNull String password) throws JnqException {
        this(driverCls, SQLHelper.getConnection(url, username, password));
    }

    public SQLConnection(@NonNull String url, @NonNull String username, @NonNull String password) throws JnqException {
        this(null, url, username, password);
    }

    protected void _updateTableContents(boolean appendSchema, @NonNull SchemaContent schema)
    throws JnqException {

        try {
            Response tablesResponse = createRequest(schema)
                    .toFactory()
                    .fromQuery(SHOW_TABLES_QUERY + (appendSchema ? schema.getName() : ""))

                    .compile()
                    .fetchTransaction();

            for (ResponseLine tableResponseLine : tablesResponse) {

                String table = tableResponseLine.nextNullableString();
                String tableFull = String.format(TABLE_NAME_FORMAT, schema.getName(), table);

                tableByNameMap.put(tableFull.toLowerCase(), new TableContent(table, schema));
            }
        }
        catch (JnqException exception) {
            throw new JnqException("tables contents update", exception);
        }
    }

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    public void updateContents() throws JnqException {
        tableByNameMap.clear();
        schemaByNamesMap.clear();

        try {
            String baseSchemaName = sqlConnection.getSchema();

            if (baseSchemaName == null) {

                try (PreparedStatement statement = sqlConnection.prepareStatement(SHOW_DATABASES_QUERY, Statement.NO_GENERATED_KEYS);
                     ResultSet resultSet = statement.executeQuery()) {

                    SQLResponse response = new SQLResponse(resultSet);

                    for (ResponseLine schemaResponseLine : response) {
                        String schema = schemaResponseLine.nextNullableString();

                        SchemaContent schemaContent = new SchemaContent(schema, this);

                        schemaByNamesMap.put(schema.toLowerCase(), schemaContent);
                        this._updateTableContents(true, schemaContent);
                    }
                }

            } else {
                SchemaContent schemaContent = new SchemaContent(baseSchemaName, this);

                schemaByNamesMap.put(baseSchemaName.toLowerCase(), schemaContent);
                this._updateTableContents(false, schemaContent);
            }
        }
        catch (SQLException exception) {
            throw new JnqException("contents update", exception);
        }
    }

    @SneakyThrows
    @Override
    public boolean checkConnection(int timeout) {
        return sqlConnection != null && sqlConnection.isValid(timeout);
    }

    @Override
    public SchemaContent getSchemaContent(@NonNull String name) {
        return schemaByNamesMap.computeIfAbsent(name.toLowerCase(), f -> new SchemaContent(name, this));
    }

    @Override
    public @NonNull Set<SchemaContent> getSchemasContents() {
        return new HashSet<>(schemaByNamesMap.values());
    }

    @Override
    public TableContent getTableContent(@NonNull String schema, @NonNull String name) {
        return tableByNameMap.get(String.format(TABLE_NAME_FORMAT, schema, name).toLowerCase());
    }

    @Override
    public TableContent getTableContent(@NonNull DataContent schema, @NonNull String name) {
        return getTableContent(schema.getName(), name);
    }

    @Override
    public @NonNull Set<TableContent> getTablesContents(@NonNull String schema) {
        return tableByNameMap.values().stream().filter(tableContent -> tableContent.getSchema().getName().equalsIgnoreCase(schema)).collect(Collectors.toSet());
    }

    @Override
    public @NonNull Request createRequest(@NonNull DataContent content) {
        return new SQLRequest(this, content);
    }

    @Override
    public @NonNull ObjectMappingService<MappingDataField> getObjectMappings() {
        return objectMappingService;
    }

    @Override
    public CompletableFuture<Void> close() throws JnqException {
        CompletableFuture<Void> completableFuture = new CompletableFuture<>();

        if (checkConnection()) {
            try {
                sqlConnection.close();

                completableFuture.complete(null);
            }
            catch (SQLException exception) {
                completableFuture.completeExceptionally(exception);

                throw new JnqException("close", exception);
            }
        }

        return completableFuture;
    }
}
