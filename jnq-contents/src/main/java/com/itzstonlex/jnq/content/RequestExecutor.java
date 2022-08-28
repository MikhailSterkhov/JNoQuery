package com.itzstonlex.jnq.content;

import com.itzstonlex.jnq.content.exception.JnqContentException;
import lombok.NonNull;

import java.util.concurrent.CompletableFuture;

public interface RequestExecutor {

    @NonNull
    Response fetchTransaction() throws JnqContentException;

    @NonNull
    UpdateResponse updateTransaction() throws JnqContentException;

    @NonNull
    ResponseLine fetchFirstLine() throws JnqContentException;

    @NonNull
    ResponseLine fetchLastLine() throws JnqContentException;

    @NonNull
    CompletableFuture<Response> fetchTransactionAsync(boolean autojoin);

    @NonNull
    CompletableFuture<ResponseLine> fetchFirstLineAsync(boolean autojoin);

    @NonNull
    CompletableFuture<ResponseLine> fetchLastLineAsync(boolean autojoin);

    @NonNull
    CompletableFuture<UpdateResponse> updateTransactionAsync(boolean autojoin);

    @NonNull
    default CompletableFuture<Response> fetchTransactionAsync() {
        return fetchTransactionAsync(true);
    }

    @NonNull
    default CompletableFuture<ResponseLine> fetchFirstLineAsync() {
        return fetchFirstLineAsync(true);
    }

    @NonNull
    default CompletableFuture<ResponseLine> fetchLastLineAsync() {
        return fetchLastLineAsync(true);
    }

    @NonNull
    default CompletableFuture<UpdateResponse> updateTransactionAsync() {
        return updateTransactionAsync(true);
    }

}
