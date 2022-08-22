package com.itzstonlex.jnq.impl.content;

import com.itzstonlex.jnq.DataConnection;
import com.itzstonlex.jnq.content.DataContent;
import com.itzstonlex.jnq.response.UpdateResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@ToString
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PROTECTED, makeFinal = true)
public abstract class SchemaContent implements DataContent {

    @ToString.Include
    @Getter
    String name;

    @Getter
    DataConnection connection;

    Map<String, TableContent> tablesMap = new ConcurrentHashMap<>();

    public abstract TableContent newTableInstance(@NonNull String name);

    public boolean isTableActive(@NonNull String name) {
        return tablesMap.containsKey(name.toLowerCase());
    }

    public @NonNull Set<TableContent> getActiveTables() {
        return new HashSet<>(tablesMap.values());
    }

    public @NonNull Set<String> getActiveTablesNames() {
        return tablesMap.values().stream().map(TableContent::getName).collect(Collectors.toSet());
    }

    public TableContent getTableByName(@NonNull String name) {
        return tablesMap.get(name.toLowerCase());
    }

    public @NonNull CompletableFuture<UpdateResponse> executeCreate() {
        return connection.createRequest(this)
                .toFactory()
                .newCreateSchema()

                .withExistsChecking()

                .compile()
                .updateTransactionAsync();
    }

    public @NonNull CompletableFuture<UpdateResponse> executeClear() {
        CompletableFuture<UpdateResponse> completableFuture = new CompletableFuture<>();

        for (TableContent tableContent : getActiveTables()) {
            completableFuture.acceptEither(tableContent.executeDrop(), (updateResponse) -> {});
        }

        return completableFuture;
    }

    public @NonNull CompletableFuture<UpdateResponse> executeDrop() {
        return connection.createRequest(this)
                .toFactory()
                .newDropSchema()

                .compile()
                .updateTransactionAsync();
    }

}
