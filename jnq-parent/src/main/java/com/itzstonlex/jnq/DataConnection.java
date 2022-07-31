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

    @NonNull
    Set<DataTableContent> getTablesContents();

    DataTableContent getTableContent(String name);

    @NonNull
    DataValidator getValidator();

    @NonNull
    DataConnectionMeta getMeta();

    @NonNull
    DataMapProvider getDataMapProvider();

    @NonNull
    Request createRequest(@NonNull DataTableContent content);

    @NonNull
    CompletableFuture<Void> close() throws SQLException;
}
