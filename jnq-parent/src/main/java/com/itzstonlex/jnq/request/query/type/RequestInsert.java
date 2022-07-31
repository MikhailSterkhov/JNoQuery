package com.itzstonlex.jnq.request.query.type;

import com.itzstonlex.jnq.field.impl.ValueDataField;
import com.itzstonlex.jnq.field.request.FieldSetRequest;
import com.itzstonlex.jnq.request.query.RequestQueryType;
import lombok.NonNull;

public interface RequestInsert extends RequestQueryType, FieldSetRequest<RequestInsert, ValueDataField> {

    @NonNull RequestInsert withIgnored();

    @NonNull RequestInsert withUpdateDuplicatedKeys();
}
