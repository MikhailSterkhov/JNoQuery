package com.itzstonlex.jnq.content.type;

import com.itzstonlex.jnq.content.JnqContent;
import com.itzstonlex.jnq.content.JnqContentMeta;
import com.itzstonlex.jnq.content.UpdateResponse;
import com.itzstonlex.jnq.content.exception.JnqContentException;
import com.itzstonlex.jnq.content.field.type.IndexField;
import com.itzstonlex.jnq.content.request.session.RequestSessionCollection;
import com.itzstonlex.jnq.content.request.type.RequestCreateTable;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.util.concurrent.CompletableFuture;

@Data
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public abstract class TableContent implements JnqContent {

    @ToString.Include
    String name;

    SchemaContent schema;

    public boolean exists() {
        return schema.isTableActive(name);
    }

    public @NonNull RequestSessionCollection<IndexField, RequestCreateTable> newCreateSession() {
        return createRequest()
                .toFactory()
                .newCreateTable()

                .checkAvailability()
                .beginCollection();
    }

    @Override
    public boolean isValid(int timeout) {
        return schema.isValid(timeout);
    }

    @Override
    public @NonNull JnqContentMeta getMeta() {
        return schema.getMeta();
    }

    public @NonNull CompletableFuture<UpdateResponse> executeClear() {
        return createRequest()
                .toFactory()
                .newDelete()

                .compile()
                .updateTransactionAsync();
    }

    public @NonNull CompletableFuture<UpdateResponse> executeDrop() {
        return createRequest()
                .toFactory()
                .newDropTable()

                .compile()
                .updateTransactionAsync();
    }

    @Override
    public @NonNull CompletableFuture<Void> closeConnection()
    throws JnqContentException {
        return schema.closeConnection();
    }

}
