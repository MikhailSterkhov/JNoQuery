package com.itzstonlex.jnq.sql;

import com.itzstonlex.jnq.DataConnectionMeta;
import com.itzstonlex.jnq.impl.decorator.DataResponseDecorator;
import com.itzstonlex.jnq.meta.ConnectionStore;
import com.itzstonlex.jnq.meta.ConnectionSupport;
import com.itzstonlex.jnq.meta.ConnectionUse;
import com.itzstonlex.jnq.response.DataResponse;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;

import java.sql.Connection;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SqlConnectionMeta implements DataConnectionMeta {

    Connection impl;

    @SneakyThrows
    @Override
    public String getURL() {
        return impl.getMetaData().getURL();
    }

    @SneakyThrows
    @Override
    public String getUsername() {
        return impl.getMetaData().getUserName();
    }

    @SneakyThrows
    @Override
    public String getDatabaseProductName() {
        return impl.getMetaData().getDatabaseProductName();
    }

    @SneakyThrows
    @Override
    public String getDatabaseProductVersion() {
        return impl.getMetaData().getDatabaseProductVersion();
    }

    @SneakyThrows
    @Override
    public String getDriverName() {
        return impl.getMetaData().getDriverName();
    }

    @SneakyThrows
    @Override
    public String getDriverVersion() {
        return impl.getMetaData().getDriverVersion();
    }

    @SneakyThrows
    @Override
    public int getDriverMajorVersion() {
        return impl.getMetaData().getDriverMajorVersion();
    }

    @SneakyThrows
    @Override
    public int getDriverMinorVersion() {
        return impl.getMetaData().getDriverMinorVersion();
    }

    @SneakyThrows
    @Override
    public boolean allProceduresAreCallable() {
        return impl.getMetaData().allProceduresAreCallable();
    }

    @SneakyThrows
    @Override
    public boolean allTablesAreSelectable() {
        return impl.getMetaData().allTablesAreSelectable();
    }

    @SneakyThrows
    @Override
    public boolean isReadOnly() {
        return impl.getMetaData().isReadOnly();
    }

    @Override
    public boolean isSupported(@NonNull ConnectionSupport support) {
        return false;
    }

    @Override
    public boolean isStored(@NonNull ConnectionStore store) {
        return false;
    }

    @Override
    public boolean isUsed(@NonNull ConnectionUse use) {
        return false;
    }

    @SneakyThrows
    @Override
    public DataResponse getTables(String catalog, String schemaPattern, String tableNamePattern, String[] types) {
        return new DataResponseDecorator(impl.getMetaData().getTables(catalog, schemaPattern, tableNamePattern, types));
    }

    @SneakyThrows
    @Override
    public DataResponse getSchemas() {
        return new DataResponseDecorator(impl.getMetaData().getSchemas());
    }

    @SneakyThrows
    @Override
    public DataResponse getCatalogs() {
        return new DataResponseDecorator(impl.getMetaData().getCatalogs());
    }

    @SneakyThrows
    @Override
    public DataResponse getTableTypes() {
        return new DataResponseDecorator(impl.getMetaData().getTableTypes());
    }
}
