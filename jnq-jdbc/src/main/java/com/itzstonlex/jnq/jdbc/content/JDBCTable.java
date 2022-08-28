package com.itzstonlex.jnq.jdbc.content;

import com.itzstonlex.jnq.content.type.TableContent;
import com.itzstonlex.jnq.jdbc.request.JDBCRequest;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

import java.sql.Connection;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JDBCTable extends TableContent implements JDBCContent {

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
        return new JDBCRequest(this);
    }
}
