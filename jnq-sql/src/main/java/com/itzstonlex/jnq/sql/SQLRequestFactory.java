package com.itzstonlex.jnq.sql;

import com.itzstonlex.jnq.content.DataContent;
import com.itzstonlex.jnq.field.DataField;
import com.itzstonlex.jnq.request.RequestFactory;
import com.itzstonlex.jnq.request.query.RequestQueryBasic;
import com.itzstonlex.jnq.request.query.type.*;
import com.itzstonlex.jnq.sql.request.SQLRequestQueryBasic;
import com.itzstonlex.jnq.sql.request.type.SQLRequestFinder;
import com.itzstonlex.jnq.sql.request.type.SQLRequestUpdate;
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
        throw new UnsupportedOperationException();
    }

    @Override
    public @NonNull RequestAlterDrop newAlterDrop() {
        throw new UnsupportedOperationException();
    }

    @Override
    public @NonNull RequestAlterUpdate newAlterUpdate() {
        throw new UnsupportedOperationException();
    }

    @Override
    public @NonNull RequestCreateTable newCreateTable() {
        throw new UnsupportedOperationException();
    }

    @Override
    public @NonNull RequestDropTable newDropTable() {
        throw new UnsupportedOperationException();
    }

    @Override
    public @NonNull RequestCreateSchema newCreateSchema() {
        throw new UnsupportedOperationException();
    }

    @Override
    public @NonNull RequestDropSchema newDropSchema() {
        throw new UnsupportedOperationException();
    }

    @Override
    public @NonNull RequestFinder newFinder() {
        return new SQLRequestFinder(request);
    }

    @Override
    public @NonNull RequestInsert newInsert() {
        throw new UnsupportedOperationException();
    }

    @Override
    public @NonNull RequestUpdate newUpdate() {
        return new SQLRequestUpdate(request);
    }

    @Override
    public @NonNull RequestDelete newDelete() {
        throw new UnsupportedOperationException();
    }
}
