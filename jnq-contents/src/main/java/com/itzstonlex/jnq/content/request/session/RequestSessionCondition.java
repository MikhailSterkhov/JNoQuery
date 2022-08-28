package com.itzstonlex.jnq.content.request.session;

import com.itzstonlex.jnq.content.field.FieldOperator;
import com.itzstonlex.jnq.content.field.type.EntryField;
import com.itzstonlex.jnq.content.request.RequestQuery;
import lombok.NonNull;

public interface RequestSessionCondition<Query extends RequestQuery> extends RequestSession<Query>  {

    @NonNull
    RequestSessionCondition<Query> and(@NonNull FieldOperator operator, @NonNull EntryField field);

    @NonNull
    RequestSessionCondition<Query> or(@NonNull FieldOperator operator, @NonNull EntryField field);

    @NonNull
    default RequestSessionCondition<Query> and(@NonNull EntryField field) {
        return and(FieldOperator.EQUAL, field);
    }

    @NonNull
    default RequestSessionCondition<Query> or(@NonNull EntryField field) {
        return or(FieldOperator.EQUAL, field);
    }
}
