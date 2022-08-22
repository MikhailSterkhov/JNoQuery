package com.itzstonlex.jnq.jdbc.content;

import com.itzstonlex.jnq.DataConnection;
import com.itzstonlex.jnq.exception.JnqException;
import com.itzstonlex.jnq.impl.content.SchemaContent;
import com.itzstonlex.jnq.impl.content.TableContent;
import com.itzstonlex.jnq.jdbc.JDBCConnection;
import com.itzstonlex.jnq.jdbc.JDBCConnectionMeta;
import com.itzstonlex.jnq.jdbc.JDBCHelper;
import com.itzstonlex.jnq.jdbc.JDBCStatement;
import com.itzstonlex.jnq.jdbc.request.JDBCRequest;
import com.itzstonlex.jnq.request.Request;
import com.itzstonlex.jnq.response.Response;
import com.itzstonlex.jnq.response.ResponseLine;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JDBCSchema extends SchemaContent implements JDBCDataContent {

    JDBCConnection jdbcConnection;

    @NonFinal
    Connection connection;

    @Getter
    @NonFinal
    JDBCConnectionMeta meta;

    public JDBCSchema(@NonNull String name, @NonNull JDBCConnection jdbcConnection) {
        super(name, jdbcConnection);
        this.jdbcConnection = jdbcConnection;
    }

    public void updateTablesData()
    throws JnqException {

        synchronized (connection) {

            if (isValid()) {
                Response response = jdbcConnection.createRequest(this)
                        .toFactory()
                        .fromQuery("SHOW TABLES;")

                        .compile()
                        .fetchTransaction();

                for (ResponseLine responseLine : response) {
                    String name = responseLine.nextNullableString();

                    super.tablesMap.put(name.toLowerCase(), newTableInstance(name));
                }
            }
        }
    }

    @SneakyThrows({SQLException.class})
    private void refreshConnection() {
        Connection baseConnection = JDBCHelper.getConnection(
                jdbcConnection.getJdbcUrl(),

                jdbcConnection.getUsername(),
                jdbcConnection.getPassword()
        );

        try (PreparedStatement statement = baseConnection.prepareStatement("CREATE SCHEMA IF NOT EXISTS `" + name + "`")) {
            statement.executeUpdate();

            baseConnection.setSchema(name);

            this.connection = baseConnection;
            this.meta = new JDBCConnectionMeta(baseConnection.getMetaData());
        }
    }

    @Override
    public final Connection getJdbcConnection() {
        if (!isValid()) {
            refreshConnection();
        }

        return connection;
    }

    @SneakyThrows({SQLException.class})
    @Override
    public boolean isValid(int timeout) {
        if (connection == null) {
            return false;
        }

        synchronized (connection) {
            return !connection.isClosed() && connection.isValid(timeout);
        }
    }

    @Override
    public JDBCConnection getConnection() {
        return jdbcConnection;
    }

    @Override
    public @NonNull JDBCRequest createRequest() {
        return new JDBCRequest(jdbcConnection, this);
    }

    @Override
    public CompletableFuture<Void> closeConnection()
    throws JnqException {

        CompletableFuture<Void> completableFuture = new CompletableFuture<>();

        if (connection == null) {
            return completableFuture;
        }

        synchronized (connection) {

            if (isValid()) {
                try {
                    connection.close();

                    completableFuture.complete(null);
                } catch (SQLException exception) {
                    completableFuture.completeExceptionally(exception);

                    throw new JnqException("close", exception);
                }
            }
        }

        return completableFuture;
    }

    @Override
    public JDBCTable newTableInstance(@NonNull String name) {
        return new JDBCTable(name, this);
    }

    @Override
    public JDBCTable getTableByName(@NonNull String name) {
        return (JDBCTable) super.getTableByName(name);
    }
}
