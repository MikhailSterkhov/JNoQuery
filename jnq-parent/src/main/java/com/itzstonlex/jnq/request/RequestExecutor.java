package com.itzstonlex.jnq.request;

import com.itzstonlex.jnq.exception.JnqException;
import com.itzstonlex.jnq.response.UpdateResponse;
import com.itzstonlex.jnq.response.Response;
import com.itzstonlex.jnq.response.ResponseLine;
import lombok.NonNull;

import java.util.concurrent.CompletableFuture;

public interface RequestExecutor {

    @NonNull
    Response fetchTransaction() throws JnqException;

    @NonNull
    UpdateResponse updateTransaction() throws JnqException;

    @NonNull
    ResponseLine fetchFirstLine() throws JnqException;

    @NonNull
    ResponseLine fetchLastLine() throws JnqException;

    @NonNull
    CompletableFuture<Response> fetchTransactionAsync();

    @NonNull
    CompletableFuture<ResponseLine> fetchFirstLineAsync();

    @NonNull
    CompletableFuture<ResponseLine> fetchLastLineAsync();

    @NonNull
    CompletableFuture<UpdateResponse> updateTransactionAsync();
}
