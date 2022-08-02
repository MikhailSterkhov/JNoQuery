package com.itzstonlex.jnq.request;

import com.itzstonlex.jnq.exception.JnqException;
import com.itzstonlex.jnq.response.UpdateResponse;
import com.itzstonlex.jnq.response.Response;
import com.itzstonlex.jnq.response.ResponseLine;
import lombok.NonNull;

import java.util.concurrent.CompletableFuture;

public interface RequestExecutor {

    @NonNull
    CompletableFuture<Response> fetchSync() throws JnqException;

    @NonNull
    CompletableFuture<Response> fetchAsync() throws JnqException;

    @NonNull
    CompletableFuture<ResponseLine> fetchFirst() throws JnqException;

    @NonNull
    CompletableFuture<ResponseLine> fetchLast() throws JnqException;

    @NonNull
    CompletableFuture<UpdateResponse> updateTransaction() throws JnqException;
}
