package com.itzstonlex.jnq.sql;

import com.itzstonlex.jnq.exception.JnqException;
import com.itzstonlex.jnq.impl.field.MappingDataField;
import com.itzstonlex.jnq.orm.ObjectMappingService;
import com.itzstonlex.jnq.orm.exception.JnqObjectMappingException;
import com.itzstonlex.jnq.request.RequestExecutor;
import com.itzstonlex.jnq.response.Response;
import com.itzstonlex.jnq.response.ResponseLine;
import com.itzstonlex.jnq.response.UpdateResponse;
import com.itzstonlex.jnq.util.JnqSupplier;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.LinkedList;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PROTECTED, makeFinal = true)
public class SQLRequestExecutor implements RequestExecutor {

    String query;
    SQLWrapperStatement wrapperStatement;

    ObjectMappingService<MappingDataField> objectMappingService;

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

    private @NonNull <T> CompletableFuture<T> _executeAsyncTransaction(boolean autojoin, @NonNull JnqSupplier<T> supplier) {
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

    @Override
    public CompletableFuture<Integer> map(@NonNull Object object) throws JnqObjectMappingException {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> @NonNull T fetchFirst(@NonNull Class<T> cls) throws JnqObjectMappingException {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> @NonNull T fetchLast(@NonNull Class<T> cls) throws JnqObjectMappingException {
        throw new UnsupportedOperationException();
    }

    @Override
    public @NonNull <T> LinkedList<T> fetchAll(@NonNull Class<T> cls) throws JnqObjectMappingException {
        throw new UnsupportedOperationException();
    }

}
