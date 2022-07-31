package com.itzstonlex.jnq.request;

import com.itzstonlex.jnq.content.DataExecutableContent;
import com.itzstonlex.jnq.request.query.type.*;
import lombok.NonNull;

public interface RequestFactory {

    @NonNull
    DataExecutableContent getExecutableContent();

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
    RequestCreateScheme newCreateScheme();

    @NonNull
    RequestDropScheme newDropScheme();

    @NonNull
    RequestFind newFind();

    @NonNull
    RequestInsert newInsert();

    @NonNull
    RequestUpdate newUpdate();

    @NonNull
    RequestDelete newDelete();
}
