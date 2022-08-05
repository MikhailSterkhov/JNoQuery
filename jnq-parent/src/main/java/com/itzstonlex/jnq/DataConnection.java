package com.itzstonlex.jnq;

import com.itzstonlex.jnq.content.DataContent;
import com.itzstonlex.jnq.exception.JnqException;
import com.itzstonlex.jnq.impl.content.SchemaContent;
import com.itzstonlex.jnq.impl.content.TableContent;
import com.itzstonlex.jnq.impl.field.MappingDataField;
import com.itzstonlex.jnq.orm.ObjectMappingService;
import com.itzstonlex.jnq.request.Request;
import lombok.NonNull;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public interface DataConnection {

    void updateContents() throws JnqException;

    boolean checkConnection(int timeout);

    default boolean checkConnection() {
        return checkConnection(100);
    }

    SchemaContent getSchemaContent(@NonNull String name);

    TableContent getTableContent(@NonNull String schema, @NonNull String name);

    TableContent getTableContent(@NonNull DataContent schema, @NonNull String name);

    @NonNull
    Set<SchemaContent> getSchemasContents();

    @NonNull
    Set<TableContent> getTablesContents(@NonNull String schema);

    @NonNull
    Request createRequest(@NonNull DataContent executableContent);

    @NonNull
    DataConnectionMeta getMeta();

    @NonNull
    ObjectMappingService<MappingDataField> getObjectMappings();

    @NonNull
    CompletableFuture<Void> close() throws JnqException;
}
