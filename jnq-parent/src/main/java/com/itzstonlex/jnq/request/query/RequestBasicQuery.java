package com.itzstonlex.jnq.request.query;

import com.itzstonlex.jnq.field.impl.ValueDataField;
import com.itzstonlex.jnq.field.request.FieldSetRequest;
import com.itzstonlex.jnq.field.request.FieldWhereRequest;

public interface RequestBasicQuery extends RequestQueryType, FieldSetRequest<RequestBasicQuery, ValueDataField>,
        FieldWhereRequest<RequestBasicQuery, ValueDataField> {
}
