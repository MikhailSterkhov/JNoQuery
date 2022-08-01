package com.itzstonlex.jnq.impl.content;

import com.itzstonlex.jnq.content.DataContent;
import com.itzstonlex.jnq.exception.JnqException;
import com.itzstonlex.jnq.impl.field.IndexDataField;
import com.itzstonlex.jnq.request.query.session.RequestSessionAppender;
import com.itzstonlex.jnq.request.query.type.RequestCreateTable;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

import java.util.concurrent.CompletableFuture;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TableContent implements DataContent {

    String name;

    SchemeContent scheme;

    public boolean exists() {
        return scheme.hasTableByName(name);
    }

    public @NonNull RequestSessionAppender<IndexDataField, RequestCreateTable> create() {
        return scheme.getConnection().createRequest(this)
                .toFactory()
                .newCreateTable()

                .withExistsChecking()
                .session();
    }

    public @NonNull CompletableFuture<Void> clear() throws JnqException {
        return scheme.getConnection().createRequest(this)
                .toFactory()
                .newDelete()

                .compile()
                .updateAsync();
    }

    public @NonNull CompletableFuture<Void> drop() throws JnqException {
        return scheme.getConnection().createRequest(this)
                .toFactory()
                .newDropTable()

                .compile()
                .updateAsync();
    }

}
