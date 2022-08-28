package com.itzstonlex.jnq.content;

import lombok.NonNull;

public interface JnqContentMeta {
    
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
