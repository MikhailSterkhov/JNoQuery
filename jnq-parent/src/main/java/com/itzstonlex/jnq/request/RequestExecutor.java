package com.itzstonlex.jnq.request;

import com.itzstonlex.jnq.response.DataResponse;
import lombok.NonNull;

import java.util.concurrent.CompletableFuture;

public interface RequestExecutor {

    @NonNull
    CompletableFuture<DataResponse> fetchSync();

    @NonNull
    CompletableFuture<DataResponse> fetchAsync();

    @NonNull
    CompletableFuture<Void> updateSync();

    @NonNull
    CompletableFuture<Void> updateAsync();
}
