package com.itzstonlex.jnq.sql;

import com.itzstonlex.jnq.DataConnectionMeta;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;

import java.sql.DatabaseMetaData;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SQLConnectionMeta implements DataConnectionMeta {

    DatabaseMetaData impl;

    @SneakyThrows
    @Override
    public String getURL() {
        return impl.getURL();
    }

    @SneakyThrows
    @Override
    public String getUsername() {
        return impl.getUserName();
    }

    @SneakyThrows
    @Override
    public String getDatabaseProductName() {
        return impl.getDatabaseProductName();
    }

    @SneakyThrows
    @Override
    public String getDatabaseProductVersion() {
        return impl.getDatabaseProductVersion();
    }

    @SneakyThrows
    @Override
    public boolean isReadOnly() {
        return impl.isReadOnly();
    }
}
