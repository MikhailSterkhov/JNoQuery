package com.itzstonlex.jnq.request.query.type;

import com.itzstonlex.jnq.field.impl.IndexDataField;
import com.itzstonlex.jnq.field.request.FieldSetRequest;
import com.itzstonlex.jnq.request.query.RequestQueryType;
import lombok.NonNull;

public interface RequestCreateTable extends RequestQueryType, FieldSetRequest<RequestCreateTable, IndexDataField> {

    @NonNull
    RequestCreateTable withExistsChecking();
}
