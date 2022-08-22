package com.itzstonlex.jnq.impl.content;

import com.itzstonlex.jnq.DataConnectionMeta;
import com.itzstonlex.jnq.content.DataContent;
import com.itzstonlex.jnq.exception.JnqException;
import com.itzstonlex.jnq.impl.field.IndexDataField;
import com.itzstonlex.jnq.request.query.session.RequestSessionAppender;
import com.itzstonlex.jnq.request.query.type.RequestCreateTable;
import com.itzstonlex.jnq.response.UpdateResponse;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.util.concurrent.CompletableFuture;

@Data
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public abstract class TableContent implements DataContent {

    @ToString.Include
    String name;

    SchemaContent schema;

    public boolean exists() {
        return schema.isTableActive(name);
    }

    public @NonNull RequestSessionAppender<IndexDataField, RequestCreateTable> newCreateSession() {
        return schema.getConnection().createRequest(this)
                .toFactory()
                .newCreateTable()

                .withExistsChecking()
                .session();
    }

    @Override
    public boolean isValid(int timeout) {
        return schema.isValid(timeout);
    }

    @Override
    public @NonNull DataConnectionMeta getMeta() {
        return schema.getMeta();
    }

    public @NonNull CompletableFuture<UpdateResponse> executeClear() {
        return schema.getConnection().createRequest(this)
                .toFactory()
                .newDelete()

                .compile()
                .updateTransactionAsync();
    }

    public @NonNull CompletableFuture<UpdateResponse> executeDrop() {
        return schema.getConnection().createRequest(this)
                .toFactory()
                .newDropTable()

                .compile()
                .updateTransactionAsync();
    }

    @Override
    public @NonNull CompletableFuture<Void> closeConnection()
    throws JnqException {
        return schema.closeConnection();
    }

}
