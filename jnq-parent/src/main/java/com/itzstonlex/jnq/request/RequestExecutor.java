package com.itzstonlex.jnq.request;

import com.itzstonlex.jnq.response.Response;
import com.itzstonlex.jnq.response.ResponseLine;
import lombok.NonNull;

import java.util.concurrent.CompletableFuture;

public interface RequestExecutor {

    @NonNull
    CompletableFuture<Response> fetchSync();

    @NonNull
    CompletableFuture<ResponseLine> fetchFirstSync();

    @NonNull
    CompletableFuture<ResponseLine> fetchLastSync();

    @NonNull
    CompletableFuture<Response> fetchAsync();

    @NonNull
    CompletableFuture<ResponseLine> fetchFirstAsync();

    @NonNull
    CompletableFuture<ResponseLine> fetchLastAsync();

    @NonNull
    CompletableFuture<Void> updateSync();

    @NonNull
    CompletableFuture<Void> updateAsync();
}
