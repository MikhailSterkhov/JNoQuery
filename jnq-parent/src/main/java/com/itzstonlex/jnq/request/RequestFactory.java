package com.itzstonlex.jnq.request;

import com.itzstonlex.jnq.content.DataContent;
import com.itzstonlex.jnq.field.DataField;
import com.itzstonlex.jnq.request.query.RequestQueryBasic;
import com.itzstonlex.jnq.request.query.type.*;
import lombok.NonNull;

public interface RequestFactory {

    @NonNull
    DataContent getExecutableContent();

    @NonNull
    <Field extends DataField> RequestQueryBasic<Field> fromQuery(@NonNull String query);

    @NonNull
    RequestAlterAdd newAlterAdd();

    @NonNull
    RequestAlterDrop newAlterDrop();

    @NonNull
    RequestAlterUpdate newAlterUpdate();

    @NonNull
    RequestCreateTable newCreateTable();

    @NonNull
    RequestDropTable newDropTable();

    @NonNull
    RequestCreateSchema newCreateSchema();

    @NonNull
    RequestDropSchema newDropSchema();

    @NonNull
    RequestFinder newFinder();

    @NonNull
    RequestInsert newInsert();

    @NonNull
    RequestUpdate newUpdate();

    @NonNull
    RequestDelete newDelete();
}
