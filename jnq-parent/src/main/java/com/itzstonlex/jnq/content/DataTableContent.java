package com.itzstonlex.jnq.content;

import com.itzstonlex.jnq.field.impl.IndexDataField;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

import java.util.concurrent.CompletableFuture;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DataTableContent implements DataExecutableContent {

    String name;

    DataSchemeContent scheme;

    public boolean exists() {
        return scheme.getTableContent(name) != null;
    }

    public @NonNull CompletableFuture<Void> create(@NonNull IndexDataField... fields) {
        return scheme.getConnection().createRequest(this).factory()
                .newCreateTable()
                .withExistsChecking()
                .set(fields)
                .complete()
                .updateAsync();
    }

    public @NonNull CompletableFuture<Void> clear() {
        return scheme.getConnection().createRequest(this).factory()
                .newDelete()
                .complete()
                .updateAsync();
    }

    public @NonNull CompletableFuture<Void> drop() {
        return scheme.getConnection().createRequest(this).factory()
                .newDelete()
                .complete()
                .updateAsync();
    }

}
