package com.itzstonlex.jnq.content;

import com.itzstonlex.jnq.content.exception.JnqContentException;
import lombok.NonNull;

import java.util.concurrent.CompletableFuture;

public interface JnqContent {

    boolean isValid(int timeout);

    default boolean isValid() {
        return isValid(1000);
    }

    @NonNull
    String getName();

    @NonNull
    JnqContentMeta getMeta();

    @NonNull
    CompletableFuture<UpdateResponse> executeClear();

    @NonNull
    Request createRequest();

    @NonNull
    CompletableFuture<UpdateResponse> executeDrop();

    @NonNull
    CompletableFuture<Void> closeConnection() throws JnqContentException;
}
