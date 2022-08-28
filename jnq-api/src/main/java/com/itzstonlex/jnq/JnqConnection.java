package com.itzstonlex.jnq;

import com.itzstonlex.jnq.content.JnqContent;
import com.itzstonlex.jnq.content.Request;
import com.itzstonlex.jnq.content.type.SchemaContent;
import com.itzstonlex.jnq.content.type.TableContent;
import lombok.NonNull;

import java.util.Set;

public interface JnqConnection {

    void updateContents();

    SchemaContent getSchema(@NonNull String name);

    TableContent getTable(@NonNull String schema, @NonNull String name);

    TableContent getTable(@NonNull JnqContent schema, @NonNull String name);

    @NonNull
    Set<SchemaContent> getActiveSchemas();

    @NonNull
    Set<TableContent> getActiveTables(@NonNull String schema);

    @NonNull
    default Request createRequest(@NonNull JnqContent content) {
        return content.createRequest();
    }
}
