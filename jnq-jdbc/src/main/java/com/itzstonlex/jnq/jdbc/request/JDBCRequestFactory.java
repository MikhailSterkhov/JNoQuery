package com.itzstonlex.jnq.jdbc.request;

import com.itzstonlex.jnq.content.field.DataField;
import com.itzstonlex.jnq.content.request.type.*;
import com.itzstonlex.jnq.jdbc.content.JDBCContent;
import com.itzstonlex.jnq.jdbc.request.type.*;
import com.itzstonlex.jnq.content.RequestFactory;
import com.itzstonlex.jnq.content.request.RequestQueryBasic;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PROTECTED, makeFinal = true)
public class JDBCRequestFactory implements RequestFactory {

    JDBCRequest request;

    @Override
    public @NonNull <Field extends DataField> RequestQueryBasic<Field> fromQuery(@NonNull String query) {
        return new JDBCRequestQueryBasic<>(query, request);
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
        return new JDBCRequestCreateTable(request);
    }

    @Override
    public @NonNull RequestDropTable newDropTable() {
        throw new UnsupportedOperationException();
    }

    @Override
    public @NonNull RequestCreateSchema newCreateSchema() {
        return new JDBCRequestCreateSchema(request);
    }

    @Override
    public @NonNull RequestDropSchema newDropSchema() {
        throw new UnsupportedOperationException();
    }

    @Override
    public @NonNull RequestFinder newFinder() {
        return new JDBCRequestFinder(request);
    }

    @Override
    public @NonNull RequestInsert newInsert() {
        return new JDBCRequestInsert(request);
    }

    @Override
    public @NonNull RequestUpdate newUpdate() {
        return new JDBCRequestUpdate(request);
    }

    @Override
    public @NonNull RequestDelete newDelete() {
        throw new UnsupportedOperationException();
    }
}
