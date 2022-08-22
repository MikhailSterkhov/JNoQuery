package com.itzstonlex.jnq.jdbc.request;

import com.itzstonlex.jnq.exception.JnqException;
import com.itzstonlex.jnq.jdbc.JDBCStatement;
import com.itzstonlex.jnq.request.RequestExecutor;
import com.itzstonlex.jnq.response.Response;
import com.itzstonlex.jnq.response.ResponseLine;
import com.itzstonlex.jnq.response.UpdateResponse;
import com.itzstonlex.jnq.util.JnqFactory;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PROTECTED, makeFinal = true)
public class JDBCRequestExecutor implements RequestExecutor {

    String query;
    JDBCStatement statement;

    @Override
    public @NonNull Response fetchTransaction() throws JnqException {
        return statement.fetch(query);
    }

    @Override
    public @NonNull UpdateResponse updateTransaction() throws JnqException {
        return statement.update(query);
    }

    @Override
    public @NonNull ResponseLine fetchFirstLine() throws JnqException {
        return fetchTransaction().getFirst();
    }

    @Override
    public @NonNull ResponseLine fetchLastLine() throws JnqException {
        return fetchTransaction().getLast();
    }

    private @NonNull <T> CompletableFuture<T> _executeAsyncTransaction(boolean autojoin, @NonNull JnqFactory<T> factory) {
        CompletableFuture<T> completableFuture = new CompletableFuture<>();
        CompletableFuture.runAsync(() -> {

            try {
                completableFuture.complete(factory.get());
            }
            catch (JnqException exception) {
                completableFuture.completeExceptionally(exception);
            }
        });

        // apply auto-join async transaction response
        if (autojoin) {
            completableFuture.join();
        }

        return completableFuture;
    }

    @Override
    public @NonNull CompletableFuture<Response> fetchTransactionAsync(boolean autojoin) {
        return this._executeAsyncTransaction(autojoin, this::fetchTransaction);
    }

    @Override
    public @NonNull CompletableFuture<ResponseLine> fetchFirstLineAsync(boolean autojoin) {
        return this._executeAsyncTransaction(autojoin, this::fetchFirstLine);
    }

    @Override
    public @NonNull CompletableFuture<ResponseLine> fetchLastLineAsync(boolean autojoin) {
        return this._executeAsyncTransaction(autojoin, this::fetchLastLine);
    }

    @Override
    public @NonNull CompletableFuture<UpdateResponse> updateTransactionAsync(boolean autojoin) {
        return this._executeAsyncTransaction(autojoin, this::updateTransaction);
    }

}
