package com.itzstonlex.jnq.request.query.type;

import com.itzstonlex.jnq.field.impl.ValueDataField;
import com.itzstonlex.jnq.field.request.FieldWhereRequest;
import com.itzstonlex.jnq.request.query.RequestQueryType;
import lombok.NonNull;

public interface RequestFind extends RequestQueryType, FieldWhereRequest<RequestFind, ValueDataField> {

    @NonNull
    RequestFind withSorting(@NonNull Operator operator, @NonNull ValueDataField... fields);

    @NonNull
    RequestFind withGrouping(@NonNull Operator operator, @NonNull ValueDataField... fields);

    default @NonNull RequestFind withSorting(@NonNull ValueDataField... fields) {
        return withSorting(Operator.EQUAL, fields);
    }

    default @NonNull RequestFind withGrouping(@NonNull ValueDataField... fields) {
        return withGrouping(Operator.EQUAL, fields);
    }
}
