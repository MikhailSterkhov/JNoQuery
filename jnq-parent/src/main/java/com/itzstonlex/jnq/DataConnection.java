package com.itzstonlex.jnq;

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

    DataTableContent getTableContent(String name);

    @NonNull
    Set<DataTableContent> getTablesContents();

    @NonNull
    DataValidator getValidator();

    @NonNull
    DataConnectionMeta getMeta();

    @NonNull
    Request createRequest(@NonNull DataTableContent content);

    @NonNull
    CompletableFuture<Void> close() throws SQLException;

    DataMapProvider getDataMapProvider(@NonNull Class<?> cls);

    void registerDataMapProvider(@NonNull Class<?> cls, @NonNull DataMapProvider provider);
}
