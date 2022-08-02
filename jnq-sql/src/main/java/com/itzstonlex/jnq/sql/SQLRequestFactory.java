package com.itzstonlex.jnq.sql;

import com.itzstonlex.jnq.content.DataContent;
import com.itzstonlex.jnq.field.DataField;
import com.itzstonlex.jnq.request.RequestFactory;
import com.itzstonlex.jnq.request.query.RequestQueryBasic;
import com.itzstonlex.jnq.request.query.type.*;
import com.itzstonlex.jnq.sql.request.SQLRequestQueryBasic;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@FieldDefaults(level = AccessLevel.PROTECTED, makeFinal = true)
public class SQLRequestFactory implements RequestFactory {

    SQLRequest request;

    @Override
    public @NonNull DataContent getExecutableContent() {
        return request.getDataContent();
    }

    @Override
    public @NonNull <Field extends DataField> RequestQueryBasic<Field> fromQuery(@NonNull String query) {
        return new SQLRequestQueryBasic<>(query, request);
    }

    @Override
    public @NonNull RequestAlterAdd newAlterAdd() {
        return null;
    }

    @Override
    public @NonNull RequestAlterDrop newAlterDrop() {
        return null;
    }

    @Override
    public @NonNull RequestAlterUpdate newAlterUpdate() {
        return null;
    }

    @Override
    public @NonNull RequestCreateTable newCreateTable() {
        return null;
    }

    @Override
    public @NonNull RequestDropTable newDropTable() {
        return null;
    }

    @Override
    public @NonNull RequestCreateSchema newCreateSchema() {
        return null;
    }

    @Override
    public @NonNull RequestDropSchema newDropSchema() {
        return null;
    }

    @Override
    public @NonNull RequestFinder newFinder() {
        return null;
    }

    @Override
    public @NonNull RequestInsert newInsert() {
        return null;
    }

    @Override
    public @NonNull RequestUpdate newUpdate() {
        return null;
    }

    @Override
    public @NonNull RequestDelete newDelete() {
        return null;
    }
}
