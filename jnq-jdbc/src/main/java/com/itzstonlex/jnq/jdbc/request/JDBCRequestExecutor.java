package com.itzstonlex.jnq.jdbc.request;

import com.itzstonlex.jnq.content.exception.JnqContentException;
import com.itzstonlex.jnq.JnqException;
import com.itzstonlex.jnq.jdbc.JDBCStatement;
import com.itzstonlex.jnq.content.RequestExecutor;
import com.itzstonlex.jnq.content.Response;
import com.itzstonlex.jnq.content.ResponseLine;
import com.itzstonlex.jnq.content.UpdateResponse;
import com.itzstonlex.jnq.JnqFactory;
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
    public @NonNull Response fetchTransaction() throws JnqContentException {
        return statement.wrapTransaction(connection -> statement.fetch(connection, query));
    }

    @Override
    public @NonNull UpdateResponse updateTransaction() throws JnqContentException {
        return statement.wrapTransaction(connection -> statement.update(connection, query));
    }

    @Override
    public @NonNull ResponseLine fetchFirstLine() throws JnqContentException {
        return fetchTransaction().getFirst();
    }

    @Override
    public @NonNull ResponseLine fetchLastLine() throws JnqContentException {
        return fetchTransaction().getLast();
    }

    private @NonNull <T> CompletableFuture<T> _executeAsyncTransaction(boolean autojoin, @NonNull JnqFactory<T> factory) {
        CompletableFuture<T> completableFuture = new CompletableFuture<>();
        CompletableFuture.runAsync(() -> {

            try {
                completableFuture.complete(factory.create());
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
