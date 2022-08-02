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

    @Override
    public @NonNull CompletableFuture<Response> fetchTransactionAsync() {
        CompletableFuture<Response> completableFuture = new CompletableFuture<>();
        CompletableFuture.runAsync(() -> {

            try {
                completableFuture.complete(fetchTransaction());
            }
            catch (JnqException exception) {
                completableFuture.completeExceptionally(exception);
            }
        });

        return completableFuture;
    }

    @Override
    public @NonNull CompletableFuture<ResponseLine> fetchFirstLineAsync() {
        CompletableFuture<ResponseLine> completableFuture = new CompletableFuture<>();
        CompletableFuture.runAsync(() -> {

            try {
                completableFuture.complete(fetchFirstLine());
            }
            catch (JnqException exception) {
                completableFuture.completeExceptionally(exception);
            }
        });

        return completableFuture;
    }

    @Override
    public @NonNull CompletableFuture<ResponseLine> fetchLastLineAsync() {
        CompletableFuture<ResponseLine> completableFuture = new CompletableFuture<>();
        CompletableFuture.runAsync(() -> {

            try {
                completableFuture.complete(fetchLastLine());
            }
            catch (JnqException exception) {
                completableFuture.completeExceptionally(exception);
            }
        });

        return completableFuture;
    }

    @Override
    public @NonNull CompletableFuture<UpdateResponse> updateTransactionAsync() {
        CompletableFuture<UpdateResponse> completableFuture = new CompletableFuture<>();
        CompletableFuture.runAsync(() -> {

            try {
                completableFuture.complete(updateTransaction());
            }
            catch (JnqException exception) {
                completableFuture.completeExceptionally(exception);
            }
        });

        return completableFuture;
    }
}
