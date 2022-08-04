package com.itzstonlex.jnq.request;

import com.itzstonlex.jnq.exception.JnqException;
import com.itzstonlex.jnq.orm.request.MappingRequestExecutor;
import com.itzstonlex.jnq.response.Response;
import com.itzstonlex.jnq.response.ResponseLine;
import com.itzstonlex.jnq.response.UpdateResponse;
import lombok.NonNull;

import java.util.concurrent.CompletableFuture;

public interface RequestExecutor extends MappingRequestExecutor {

    @NonNull
    Response fetchTransaction() throws JnqException;

    @NonNull
    UpdateResponse updateTransaction() throws JnqException;

    @NonNull
    ResponseLine fetchFirstLine() throws JnqException;

    @NonNull
    ResponseLine fetchLastLine() throws JnqException;

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
