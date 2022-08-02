package com.itzstonlex.jnq.content;

import com.itzstonlex.jnq.exception.JnqException;
import com.itzstonlex.jnq.response.UpdateResponse;
import lombok.NonNull;

import java.util.concurrent.CompletableFuture;

public interface DataContent {

    @NonNull
    String getName();

    @NonNull
    CompletableFuture<UpdateResponse> clear() throws JnqException;

    @NonNull
    CompletableFuture<UpdateResponse> drop() throws JnqException;
}
