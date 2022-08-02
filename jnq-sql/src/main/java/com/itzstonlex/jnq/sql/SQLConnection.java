package com.itzstonlex.jnq.sql;

import com.itzstonlex.jnq.DataConnection;
import com.itzstonlex.jnq.content.DataContent;
import com.itzstonlex.jnq.exception.JnqException;
import com.itzstonlex.jnq.impl.content.SchemaContent;
import com.itzstonlex.jnq.impl.content.TableContent;
import com.itzstonlex.jnq.sql.response.SQLResponse;
import com.itzstonlex.jnq.request.Request;
import com.itzstonlex.jnq.response.ResponseLine;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;

import java.sql.Connection;
import java.sql.SQLException;
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

    Map<String, SchemaContent> schemaByNamesMap = new ConcurrentHashMap<>();
    Map<String, TableContent> tableByNameMap = new ConcurrentHashMap<>();

    public SQLConnection(String driverCls, @NonNull Connection sqlConnection) throws JnqException {
        try {
            if (driverCls != null) {
                Class.forName(driverCls);
            }

            this.sqlConnection = sqlConnection;
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

    protected void _updateTableContents(String schema)
    throws JnqException {

        try {
            String query = SHOW_TABLES_QUERY + (schema != null ? schema : "");

            if (schema == null) {
                schema = sqlConnection.getSchema();
            }

            for (ResponseLine tableResponseLine : new SQLResponse(sqlConnection.prepareStatement(query).executeQuery())) {

                String table = tableResponseLine.nextString();
                String tableFull = String.format(TABLE_NAME_FORMAT, schema, table);

                tableByNameMap.put(tableFull.toLowerCase(), new TableContent(table, getSchemaContent(schema)));
            }
        }
        catch (SQLException exception) {
            throw new JnqException("tables contents update", exception);
        }
    }

    public void updateContents() throws JnqException {
        tableByNameMap.clear();
        schemaByNamesMap.clear();

        try {
            String baseSchemaName = sqlConnection.getSchema();

            if (baseSchemaName == null) {
                for (ResponseLine schemaResponseLine : new SQLResponse(sqlConnection.prepareStatement(SHOW_DATABASES_QUERY).executeQuery())) {

                    String schema = schemaResponseLine.nextString();

                    SchemaContent schemaContent = new SchemaContent(schema, this);

                    schemaByNamesMap.put(schema.toLowerCase(), schemaContent);
                    _updateTableContents(schema);
                }
            }
            else {
                schemaByNamesMap.put(baseSchemaName.toLowerCase(), new SchemaContent(baseSchemaName, this));
                _updateTableContents(null);
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
