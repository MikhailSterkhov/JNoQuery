package com.itzstonlex.jnq.sql;

import com.itzstonlex.jnq.DataConnection;
import com.itzstonlex.jnq.content.DataContent;
import com.itzstonlex.jnq.exception.JnqException;
import com.itzstonlex.jnq.impl.content.SchemeContent;
import com.itzstonlex.jnq.impl.content.TableContent;
import com.itzstonlex.jnq.impl.response.WrapperResponse;
import com.itzstonlex.jnq.request.Request;
import com.itzstonlex.jnq.response.ResponseLine;
import com.itzstonlex.jnq.sql.utility.SQLUtility;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SQLConnection implements DataConnection {

    private static final String SHOW_DATABASES_QUERY = "SHOW DATABASES;";
    private static final String SHOW_TABLES_QUERY = "SHOW TABLES ";

    private static final String TABLE_NAME_FORMAT = "%s.%s";

    @Getter
    private final SQLConnectionMeta meta;

    private final Connection connection;

    private final Map<String, SchemeContent> schemeByNamesMap = new ConcurrentHashMap<>();
    private final Map<String, TableContent> tableByNameMap = new ConcurrentHashMap<>();

    public SQLConnection(String driverCls, @NonNull Connection connection) throws JnqException {
        try {
            if (driverCls != null) {
                Class.forName(driverCls);
            }

            this.connection = connection;
            this.meta = new SQLConnectionMeta(connection.getMetaData());

            this._initContentData(connection);
        }
        catch (Exception exception) {
            throw new JnqException("init", exception);
        }
    }

    public SQLConnection(@NonNull Connection connection) throws JnqException {
        this(null, connection);
    }

    public SQLConnection(String driverCls, @NonNull String url, @NonNull String username, @NonNull String password) throws JnqException {
        this(driverCls, SQLUtility.getConnection(url, username, password));
    }

    public SQLConnection(@NonNull String url, @NonNull String username, @NonNull String password) throws JnqException {
        this(null, url, username, password);
    }

    private void _initContentData(@NonNull Connection connection)
    throws Exception {

        for (ResponseLine schemeResponseLine : new WrapperResponse(connection.prepareStatement(SHOW_DATABASES_QUERY).executeQuery())) {
            String scheme = schemeResponseLine.nextString();

            SchemeContent schemeContent = new SchemeContent(scheme, this);

            for (ResponseLine tableResponseLine : new WrapperResponse(connection.prepareStatement(SHOW_TABLES_QUERY + scheme).executeQuery())) {

                String table = tableResponseLine.nextString();
                String tableFull = String.format(TABLE_NAME_FORMAT, scheme, table);

                tableByNameMap.put(tableFull.toLowerCase(), new TableContent(table, schemeContent));
            }

            schemeByNamesMap.put(scheme.toLowerCase(), new SchemeContent(scheme, this));
        }
    }

    @Override
    public boolean checkConnection(int timeout) {
        return connection != null && checkConnection(timeout);
    }

    @Override
    public SchemeContent getSchemeContent(@NonNull String name) {
        return schemeByNamesMap.computeIfAbsent(name.toLowerCase(), f -> new SchemeContent(name, this));
    }

    @Override
    public @NonNull Set<SchemeContent> getSchemesContents() {
        return new HashSet<>(schemeByNamesMap.values());
    }

    @Override
    public TableContent getTableContent(@NonNull String scheme, @NonNull String name) {
        return tableByNameMap.get(String.format(TABLE_NAME_FORMAT, scheme, name).toLowerCase());
    }

    @Override
    public TableContent getTableContent(@NonNull DataContent scheme, @NonNull String name) {
        return getTableContent(scheme.getName(), name);
    }

    @Override
    public @NonNull Set<TableContent> getTablesContents(@NonNull String scheme) {
        return tableByNameMap.values().stream().filter(tableContent -> tableContent.getScheme().getName().equalsIgnoreCase(scheme)).collect(Collectors.toSet());
    }

    @Override
    public @NonNull Request createRequest(@NonNull DataContent content) {
        throw new UnsupportedOperationException();
    }

    @Override
    public CompletableFuture<Void> close() throws JnqException {
        CompletableFuture<Void> completableFuture = new CompletableFuture<>();

        if (checkConnection()) {
            try {
                connection.close();

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
