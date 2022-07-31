package com.itzstonlex.jnq;

import com.itzstonlex.jnq.meta.ConnectionStore;
import com.itzstonlex.jnq.meta.ConnectionSupport;
import com.itzstonlex.jnq.meta.ConnectionUse;
import com.itzstonlex.jnq.response.DataResponse;
import lombok.NonNull;

public interface DataConnectionMeta {
    
    String getURL();

    String getUsername();

    String getDatabaseProductName();

    String getDatabaseProductVersion();

    String getDriverName();

    String getDriverVersion();
    
    int getDriverMajorVersion();
    
    int getDriverMinorVersion();

    boolean allProceduresAreCallable();

    boolean allTablesAreSelectable();

    boolean isReadOnly();
    
    boolean isSupported(@NonNull ConnectionSupport support);

    boolean isStored(@NonNull ConnectionStore store);

    boolean isUsed(@NonNull ConnectionUse use);

    DataResponse getTables(String catalog, String schemaPattern, String tableNamePattern, String[] types);

    DataResponse getSchemas();

    DataResponse getCatalogs();

    DataResponse getTableTypes();

    default DataResponse getTables(String schemaPattern, String tableNamePattern, String[] types) {
        return getTables(null, schemaPattern, tableNamePattern, types);
    }

    default DataResponse getTables(String tableNamePattern, String[] types) {
        return getTables(null, tableNamePattern, types);
    }

    default DataResponse getTables(String tableNamePattern) {
        return getTables(tableNamePattern, null);
    }

    default DataResponse getTables() {
        return getTables("");
    }
}
