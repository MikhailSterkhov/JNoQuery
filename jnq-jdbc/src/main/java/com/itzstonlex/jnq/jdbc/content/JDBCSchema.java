package com.itzstonlex.jnq.jdbc.content;

import com.itzstonlex.jnq.content.Request;
import com.itzstonlex.jnq.content.RequestFactory;
import com.itzstonlex.jnq.content.Response;
import com.itzstonlex.jnq.content.ResponseLine;
import com.itzstonlex.jnq.content.exception.JnqContentException;
import com.itzstonlex.jnq.content.type.SchemaContent;
import com.itzstonlex.jnq.jdbc.JDBCConnection;
import com.itzstonlex.jnq.jdbc.JDBCContentMeta;
import com.itzstonlex.jnq.jdbc.JDBCHelper;
import com.itzstonlex.jnq.jdbc.request.JDBCRequest;
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
public class JDBCSchema extends SchemaContent implements JDBCContent {

    JDBCConnection jdbcConnection;

    @NonFinal
    Connection connection;

    @Getter
    @NonFinal
    JDBCContentMeta meta;

    public JDBCSchema(@NonNull String name, @NonNull JDBCConnection jdbcConnection) {
        super(name);
        this.jdbcConnection = jdbcConnection;
    }

    @Override
    public void updateTablesData()
    throws JnqContentException {

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
            this.meta = new JDBCContentMeta(baseConnection.getMetaData());
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
    public @NonNull Request createRequest() {
        return new JDBCRequest(this);
    }

    @Override
    public CompletableFuture<Void> closeConnection()
    throws JnqContentException {

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

                    throw new JnqContentException("close", exception);
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
