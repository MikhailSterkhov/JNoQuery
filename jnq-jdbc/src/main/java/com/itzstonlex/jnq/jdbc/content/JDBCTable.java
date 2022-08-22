package com.itzstonlex.jnq.jdbc.content;

import com.itzstonlex.jnq.impl.content.TableContent;
import com.itzstonlex.jnq.jdbc.request.JDBCRequest;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

import java.sql.Connection;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JDBCTable extends TableContent implements JDBCDataContent {

    JDBCSchema jdbcSchema;

    public JDBCTable(String name, JDBCSchema jdbcSchema) {
        super(name, jdbcSchema);
        this.jdbcSchema = jdbcSchema;
    }

    @Override
    public final Connection getJdbcConnection() {
        return jdbcSchema.getJdbcConnection();
    }

    @Override
    public @NonNull JDBCRequest createRequest() {
        return new JDBCRequest(jdbcSchema.getConnection(), this);
    }
}
