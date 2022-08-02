package com.itzstonlex.jnq.impl.content;

import com.itzstonlex.jnq.content.DataContent;
import com.itzstonlex.jnq.exception.JnqException;
import com.itzstonlex.jnq.impl.field.IndexDataField;
import com.itzstonlex.jnq.request.query.session.RequestSessionAppender;
import com.itzstonlex.jnq.request.query.type.RequestCreateTable;
import com.itzstonlex.jnq.response.UpdateResponse;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

import java.util.concurrent.CompletableFuture;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TableContent implements DataContent {

    String name;

    SchemaContent schema;

    public boolean exists() {
        return schema.hasTableByName(name);
    }

    public @NonNull RequestSessionAppender<IndexDataField, RequestCreateTable> create() {
        return schema.getConnection().createRequest(this)
                .toFactory()
                .newCreateTable()

                .withExistsChecking()
                .session();
    }

    public @NonNull CompletableFuture<UpdateResponse> clear() throws JnqException {
        return schema.getConnection().createRequest(this)
                .toFactory()
                .newDelete()

                .compile()
                .updateTransaction();
    }

    public @NonNull CompletableFuture<UpdateResponse> drop() throws JnqException {
        return schema.getConnection().createRequest(this)
                .toFactory()
                .newDropTable()

                .compile()
                .updateTransaction();
    }

}
