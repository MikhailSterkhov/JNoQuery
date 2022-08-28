package com.itzstonlex.jnq.content;

import com.itzstonlex.jnq.content.field.DataField;
import com.itzstonlex.jnq.content.request.type.*;
import com.itzstonlex.jnq.content.request.RequestQueryBasic;
import lombok.NonNull;

public interface RequestFactory {

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
