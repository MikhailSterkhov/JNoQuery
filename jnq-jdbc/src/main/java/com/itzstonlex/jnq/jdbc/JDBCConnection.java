package com.itzstonlex.jnq.jdbc;

import com.itzstonlex.jnq.JnqConnection;
import com.itzstonlex.jnq.content.JnqContent;
import com.itzstonlex.jnq.content.exception.JnqContentException;
import com.itzstonlex.jnq.content.type.SchemaContent;
import com.itzstonlex.jnq.content.type.TableContent;
import com.itzstonlex.jnq.JnqException;
import com.itzstonlex.jnq.jdbc.content.JDBCSchema;
import com.itzstonlex.jnq.jdbc.content.JDBCTable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@FieldDefaults(level = AccessLevel.PROTECTED, makeFinal = true)
public class JDBCConnection implements JnqConnection {

    @Getter
    String jdbcUrl, username, password;

    Map<String, JDBCSchema> jdbcSchemasMap;

    public JDBCConnection(String driverCls, @NonNull String jdbcUrl, String username, String password) throws JnqException {
        try {
            if (driverCls != null) {
                Class.forName(driverCls);
            }

            this.jdbcUrl = jdbcUrl;

            this.username = username;
            this.password = password;

            this.jdbcSchemasMap = new ConcurrentHashMap<>();
        }
        catch (Exception exception) {
            throw new JnqException("init", exception);
        }
    }

    public JDBCConnection(@NonNull String url, String username, String password) throws JnqException {
        this(null, url, username, password);
    }

    public JDBCConnection(String driverCls, @NonNull String url) throws JnqException {
        this(driverCls, url, null, null);
    }

    public JDBCConnection(@NonNull String url) throws JnqException {
        this(url, null, null);
    }

    @Override
    public void updateContents() {
        jdbcSchemasMap.values().forEach(jdbcSchema -> {

            try {
                jdbcSchema.updateTablesData();
            }
            catch (JnqContentException exception) {
                throw new RuntimeException(exception);
            }
        });
    }

    @Override
    public JDBCSchema getSchema(@NonNull String name) {
        return jdbcSchemasMap.computeIfAbsent(name.toLowerCase(), f -> new JDBCSchema(name, this));
    }

    @Override
    public @NonNull Set<SchemaContent> getActiveSchemas() {
        return new HashSet<>(jdbcSchemasMap.values());
    }

    @Override
    public JDBCTable getTable(@NonNull String schema, @NonNull String name) {
        return getSchema(schema).getTableByName(name);
    }

    @Override
    public JDBCTable getTable(@NonNull JnqContent schema, @NonNull String name) {
        return getTable(schema.getName(), name);
    }

    @Override
    public @NonNull Set<TableContent> getActiveTables(@NonNull String schema) {
        return getSchema(schema).getActiveTables();
    }

}
