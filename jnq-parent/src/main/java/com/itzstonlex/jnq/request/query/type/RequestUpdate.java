package com.itzstonlex.jnq.request.query.type;

import com.itzstonlex.jnq.field.impl.ValueDataField;
import com.itzstonlex.jnq.field.request.FieldSetRequest;
import com.itzstonlex.jnq.field.request.FieldWhereRequest;
import com.itzstonlex.jnq.request.query.RequestQueryType;

public interface RequestUpdate extends RequestQueryType, FieldSetRequest<RequestUpdate, ValueDataField>,
        FieldWhereRequest<RequestUpdate, ValueDataField> {
}
