package com.itzstonlex.jnq.jdbc;

import com.itzstonlex.jnq.DataConnection;
import com.itzstonlex.jnq.content.DataContent;
import com.itzstonlex.jnq.exception.JnqException;
import com.itzstonlex.jnq.impl.content.SchemaContent;
import com.itzstonlex.jnq.impl.content.TableContent;
import com.itzstonlex.jnq.impl.field.MappingDataField;
import com.itzstonlex.jnq.impl.orm.ObjectMappingServiceImpl;
import com.itzstonlex.jnq.jdbc.content.JDBCSchema;
import com.itzstonlex.jnq.jdbc.content.JDBCTable;
import com.itzstonlex.jnq.orm.ObjectMappingService;
import com.itzstonlex.jnq.request.Request;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@FieldDefaults(level = AccessLevel.PROTECTED, makeFinal = true)
public class JDBCConnection implements DataConnection {

    @Getter
    String jdbcUrl, username, password;

    Map<String, JDBCSchema> jdbcSchemasMap;

    ObjectMappingService<MappingDataField> ormService;

    public JDBCConnection(String driverCls, @NonNull String jdbcUrl, @NonNull String username, @NonNull String password) throws JnqException {
        try {
            if (driverCls != null) {
                Class.forName(driverCls);
            }

            this.jdbcUrl = jdbcUrl;

            this.username = username;
            this.password = password;

            this.jdbcSchemasMap = new ConcurrentHashMap<>();
            this.ormService = new ObjectMappingServiceImpl(this);
        }
        catch (Exception exception) {
            throw new JnqException("init", exception);
        }
    }

    public JDBCConnection(@NonNull String url, @NonNull String username, @NonNull String password) throws JnqException {
        this(null, url, username, password);
    }

    @Override
    public void updateContents() {
        jdbcSchemasMap.values().forEach(jdbcSchema -> {

            try {
                jdbcSchema.updateTablesData();
            }
            catch (JnqException exception) {
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
    public JDBCTable getTable(@NonNull DataContent schema, @NonNull String name) {
        return getTable(schema.getName(), name);
    }

    @Override
    public @NonNull Set<TableContent> getActiveTables(@NonNull String schema) {
        return getSchema(schema).getActiveTables();
    }

    @Override
    public @NonNull Request createRequest(@NonNull DataContent content) {
        return new JDBCRequest(this, content);
    }

    @Override
    public @NonNull ObjectMappingService<MappingDataField> getObjectMappings() {
        return ormService;
    }
}
