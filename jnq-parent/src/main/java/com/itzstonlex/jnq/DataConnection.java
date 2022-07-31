package com.itzstonlex.jnq;

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

    @NonNull
    Set<DataSchemeContent> getSchemesContents();

    DataTableContent getTableContent(@NonNull String name);

    @NonNull
    Set<DataTableContent> getTablesContents();

    @NonNull
    DataValidator getValidator();

    @NonNull
    DataConnectionMeta getMeta();

    @NonNull
    Request createRequest(@NonNull DataTableContent content);

    @NonNull
    default Request createRequest(@NonNull DataSchemeContent content) {
        DataTableContent first = content.getTables().values().iterator().next();
        return createRequest(first);
    }

    @NonNull
    CompletableFuture<Void> close() throws SQLException;

    DataMapProvider getDataMapProvider(@NonNull Class<?> cls);

    void registerDataMapProvider(@NonNull Class<?> cls, @NonNull DataMapProvider provider);
}
