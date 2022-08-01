package com.itzstonlex.jnq;

import lombok.NonNull;

public interface DataConnectionMeta {
    
    @NonNull
    String getURL();

    @NonNull
    String getUsername();

    @NonNull
    String getDatabaseProductName();

    @NonNull
    String getDatabaseProductVersion();

    boolean isReadOnly();
}
