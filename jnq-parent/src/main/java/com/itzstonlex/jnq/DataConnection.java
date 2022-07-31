package com.itzstonlex.jnq;

import com.itzstonlex.jnq.content.DataExecutableContent;
import com.itzstonlex.jnq.content.DataSchemeContent;
import com.itzstonlex.jnq.content.DataTableContent;
import com.itzstonlex.jnq.request.Request;
import lombok.NonNull;

import java.sql.SQLException;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public interface DataConnection {

    boolean checkConnection(int timeout);

    default boolean checkConnection() {
        return checkConnection(1000);
    }

    DataSchemeContent getSchemeContent(@NonNull String name);

    DataTableContent getTableContent(@NonNull String scheme, @NonNull String name);

    @NonNull
    Set<DataSchemeContent> getSchemesContents();

    @NonNull
    Set<DataTableContent> getTablesContents(@NonNull String scheme);

    @NonNull
    DataValidator getValidator();

    @NonNull
    DataConnectionMeta getMeta();

    @NonNull
    Request createRequest(@NonNull DataExecutableContent executableContent);

    @NonNull
    CompletableFuture<Void> close() throws SQLException;

    DataMapProvider getDataMapProvider(@NonNull Class<?> cls);

    void registerDataMapProvider(@NonNull Class<?> cls, @NonNull DataMapProvider provider);
}
