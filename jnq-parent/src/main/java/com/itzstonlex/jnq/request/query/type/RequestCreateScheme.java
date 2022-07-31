package com.itzstonlex.jnq.request.query.type;

import com.itzstonlex.jnq.field.impl.IndexDataField;
import com.itzstonlex.jnq.field.request.FieldSetRequest;
import com.itzstonlex.jnq.request.query.RequestQueryType;
import lombok.NonNull;

public interface RequestCreateScheme extends RequestQueryType, FieldSetRequest<RequestCreateScheme, IndexDataField> {

    @NonNull
    RequestCreateScheme withExistsChecking();
}
