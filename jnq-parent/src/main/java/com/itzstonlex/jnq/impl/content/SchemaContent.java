package com.itzstonlex.jnq.impl.content;

import com.itzstonlex.jnq.DataConnection;
import com.itzstonlex.jnq.content.DataContent;
import com.itzstonlex.jnq.exception.JnqException;
import com.itzstonlex.jnq.response.UpdateResponse;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SchemaContent implements DataContent {

    @Getter
    String name;

    @Getter
    DataConnection connection;

    public boolean hasTableByName(@NonNull String name) {
        return getTablesContents().stream().anyMatch(tableContent -> tableContent.getName().equalsIgnoreCase(name));
    }

    public @NonNull Set<TableContent> getTablesContents() {
        return connection.getTablesContents(name);
    }

    public @NonNull Set<String> getTablesNames() {
        return getTablesContents().stream().map(TableContent::getName).collect(Collectors.toSet());
    }

    public TableContent getTableContent(@NonNull String name) {
        return connection.getTableContent(this, name);
    }

    public @NonNull CompletableFuture<UpdateResponse> create()
    throws JnqException {

        return connection.createRequest(this)
                .toFactory()
                .newCreateSchema()

                .withExistsChecking()

                .compile()
                .updateTransactionAsync();
    }

    public @NonNull CompletableFuture<UpdateResponse> clear()
    throws JnqException {

        CompletableFuture<UpdateResponse> completableFuture = new CompletableFuture<>();

        for (TableContent tableContent : getTablesContents()) {
            completableFuture.acceptEither(tableContent.drop(), (updateResponse) -> {});
        }

        return completableFuture;
    }

    public @NonNull CompletableFuture<UpdateResponse> drop()
    throws JnqException {

        return connection.createRequest(this)
                .toFactory()
                .newDropSchema()

                .compile()
                .updateTransactionAsync();
    }

}
