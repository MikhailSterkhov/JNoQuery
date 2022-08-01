package com.itzstonlex.jnq.request.query.session;

import com.itzstonlex.jnq.field.FieldOperator;
import com.itzstonlex.jnq.impl.field.ValueDataField;
import com.itzstonlex.jnq.request.query.RequestQuery;
import lombok.NonNull;

public interface RequestSessionFilter<Query extends RequestQuery> extends RequestSession<Query>  {

    @NonNull
    RequestSessionFilter<Query> and(@NonNull FieldOperator operator, @NonNull ValueDataField field);

    @NonNull
    RequestSessionFilter<Query> or(@NonNull FieldOperator operator, @NonNull ValueDataField field);

    @NonNull
    default RequestSessionFilter<Query> and(@NonNull ValueDataField field) {
        return and(FieldOperator.EQUAL, field);
    }

    @NonNull
    default RequestSessionFilter<Query> or(@NonNull ValueDataField field) {
        return or(FieldOperator.EQUAL, field);
    }
}
