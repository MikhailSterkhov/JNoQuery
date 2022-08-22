package com.itzstonlex.jnq.content;

import com.itzstonlex.jnq.DataConnectionMeta;
import com.itzstonlex.jnq.exception.JnqException;
import com.itzstonlex.jnq.response.UpdateResponse;
import lombok.NonNull;

import java.util.concurrent.CompletableFuture;

public interface DataContent {

    boolean isValid(int timeout);

    default boolean isValid() {
        return isValid(1000);
    }

    @NonNull
    String getName();

    @NonNull
    DataConnectionMeta getMeta();

    @NonNull
    CompletableFuture<UpdateResponse> executeClear();

    @NonNull
    CompletableFuture<UpdateResponse> executeDrop();

    @NonNull
    CompletableFuture<Void> closeConnection() throws JnqException;
}
