package com.itzstonlex.jnq.sql;

import com.itzstonlex.jnq.exception.JnqException;
import com.itzstonlex.jnq.request.RequestExecutor;
import com.itzstonlex.jnq.response.UpdateResponse;
import com.itzstonlex.jnq.response.Response;
import com.itzstonlex.jnq.response.ResponseLine;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PROTECTED, makeFinal = true)
public class SQLRequestExecutor implements RequestExecutor {

    String query;
    SQLWrapperStatement wrapperStatement;

    @Override
    public @NonNull CompletableFuture<Response> fetchSync() throws JnqException {
        return wrapperStatement.executeFetchSync(query);
    }

    @Override
    public @NonNull CompletableFuture<Response> fetchAsync() throws JnqException {
        CompletableFuture<Response> completableFuture = wrapperStatement.executeFetchAsync(query);
        completableFuture.join();

        return completableFuture;
    }

    @Override
    public @NonNull CompletableFuture<ResponseLine> fetchFirst() throws JnqException {
        return CompletableFuture.completedFuture(this.fetchSync().join().getFirst());
    }

    @Override
    public @NonNull CompletableFuture<ResponseLine> fetchLast() throws JnqException {
        return CompletableFuture.completedFuture(this.fetchSync().join().getLast());
    }

    @Override
    public @NonNull CompletableFuture<UpdateResponse> updateTransaction() {
        CompletableFuture<UpdateResponse> completableFuture = wrapperStatement.executeUpdate(query);
        completableFuture.join();

        return completableFuture;
    }

}
