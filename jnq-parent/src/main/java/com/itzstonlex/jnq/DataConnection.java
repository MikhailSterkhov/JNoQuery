package com.itzstonlex.jnq;

import com.itzstonlex.jnq.content.DataContent;
import com.itzstonlex.jnq.impl.content.SchemeContent;
import com.itzstonlex.jnq.impl.content.TableContent;
import com.itzstonlex.jnq.exception.JnqException;
import com.itzstonlex.jnq.request.Request;
import lombok.NonNull;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public interface DataConnection {

    boolean checkConnection(int timeout);

    default boolean checkConnection() {
        return checkConnection(1000);
    }

    SchemeContent getSchemeContent(@NonNull String name);

    TableContent getTableContent(@NonNull String scheme, @NonNull String name);

    TableContent getTableContent(@NonNull DataContent scheme, @NonNull String name);

    @NonNull
    Set<SchemeContent> getSchemesContents();

    @NonNull
    Set<TableContent> getTablesContents(@NonNull String scheme);

    @NonNull
    Request createRequest(@NonNull DataContent executableContent);

    @NonNull
    DataConnectionMeta getMeta();

    @NonNull
    CompletableFuture<Void> close() throws JnqException;
}
