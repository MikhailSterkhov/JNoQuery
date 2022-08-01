package com.itzstonlex.jnq.impl.content;

import com.itzstonlex.jnq.DataConnection;
import com.itzstonlex.jnq.content.DataContent;
import com.itzstonlex.jnq.exception.JnqException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SchemeContent implements DataContent {

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

    public @NonNull TableContent getTableContent(@NonNull String name) {
        return connection.getTableContent(this, name);
    }

    public @NonNull CompletableFuture<Void> create() throws JnqException {
        return connection.createRequest(this)
                .factory()
                .newCreateScheme()

                .withExistsChecking()

                .compile()
                .updateAsync();
    }

    public @NonNull CompletableFuture<Void> clear() throws JnqException {
        Set<CompletableFuture<Void>> completableFutureSet = new HashSet<>();

        for (TableContent tableContent : getTablesContents()) {

            CompletableFuture<Void> future = tableContent.drop();
            completableFutureSet.add(future);
        }

        return CompletableFuture.allOf(completableFutureSet.toArray(new CompletableFuture[0]));
    }

    public @NonNull CompletableFuture<Void> drop() throws JnqException {
        return connection.createRequest(this)
                .factory()
                .newDropScheme()

                .compile()
                .updateAsync();
    }

}
