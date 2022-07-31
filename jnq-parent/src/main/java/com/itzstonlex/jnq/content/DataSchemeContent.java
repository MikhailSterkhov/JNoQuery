package com.itzstonlex.jnq.content;

import com.itzstonlex.jnq.DataConnection;
import com.itzstonlex.jnq.field.impl.IndexDataField;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DataSchemeContent {

    Map<String, DataTableContent> tableByNamesMap = new ConcurrentHashMap<>();

    @Getter
    String name;

    @Getter
    DataConnection connection;

    public @NonNull Set<DataTableContent> getTablesContents() {
        return new HashSet<>(tableByNamesMap.values());
    }

    public @NonNull Set<String> getTablesNames() {
        return tableByNamesMap.values().stream().map(DataTableContent::getName).collect(Collectors.toSet());
    }

    public DataTableContent getTableContent(String name) {
        return tableByNamesMap.get(name.toLowerCase());
    }

    public @NonNull CompletableFuture<Void> create(@NonNull DataTableContent table, @NonNull IndexDataField... fields) {
        return table.create(fields).thenAccept(unused -> tableByNamesMap.put(table.getName().toLowerCase(), table));
    }

    public @NonNull CompletableFuture<Void> create() {
        return connection.createRequest(this).factory()
                .newCreateScheme()
                .withExistsChecking()
                .complete()
                .updateAsync();
    }

    public @NonNull CompletableFuture<Void> clear(@NonNull DataTableContent table) {
        return table.clear();
    }

    public @NonNull CompletableFuture<Void> clear() {
        Set<CompletableFuture<Void>> completableFutureSet = new HashSet<>();

        tableByNamesMap.forEach((name, dataTableContent) -> {

            CompletableFuture<Void> future = dataTableContent.drop();
            completableFutureSet.add(future);
        });

        return CompletableFuture.allOf(completableFutureSet.toArray(new CompletableFuture[0]));
    }

    public @NonNull CompletableFuture<Void> drop(@NonNull DataTableContent table) {
        return table.drop().thenAccept(unused -> tableByNamesMap.remove(table.getName().toLowerCase()));
    }

}
