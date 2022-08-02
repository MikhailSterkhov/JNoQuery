package com.itzstonlex.jnq.sql;

import com.itzstonlex.jnq.exception.JnqException;
import com.itzstonlex.jnq.request.RequestExecutor;
import com.itzstonlex.jnq.response.UpdateResponse;
import com.itzstonlex.jnq.response.Response;
import com.itzstonlex.jnq.response.ResponseLine;
import com.itzstonlex.jnq.util.JnqSupplier;
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
    public @NonNull Response fetchTransaction() throws JnqException {
        return wrapperStatement.fetch(query);
    }

    @Override
    public @NonNull UpdateResponse updateTransaction() throws JnqException {
        return wrapperStatement.update(query);
    }

    @Override
    public @NonNull ResponseLine fetchFirstLine() throws JnqException {
        return fetchTransaction().getFirst();
    }

    @Override
    public @NonNull ResponseLine fetchLastLine() throws JnqException {
        return fetchTransaction().getLast();
    }

    private @NonNull <T> CompletableFuture<T> _executeAsyncTransaction(@NonNull JnqSupplier<T> supplier) {
        CompletableFuture<T> completableFuture = new CompletableFuture<>();
        CompletableFuture.runAsync(() -> {

            try {
                completableFuture.complete(supplier.get());
            }
            catch (JnqException exception) {
                completableFuture.completeExceptionally(exception);
            }
        });

        // apply auto-join async transaction response
        completableFuture.join();

        return completableFuture;
    }

    @Override
    public @NonNull CompletableFuture<Response> fetchTransactionAsync() {
        return this._executeAsyncTransaction(this::fetchTransaction);
    }

    @Override
    public @NonNull CompletableFuture<ResponseLine> fetchFirstLineAsync() {
        return this._executeAsyncTransaction(this::fetchFirstLine);
    }

    @Override
    public @NonNull CompletableFuture<ResponseLine> fetchLastLineAsync() {
        return this._executeAsyncTransaction(this::fetchLastLine);
    }

    @Override
    public @NonNull CompletableFuture<UpdateResponse> updateTransactionAsync() {
        return this._executeAsyncTransaction(this::updateTransaction);
    }
}