package com.itzstonlex.jnq.request.query.type;

import com.itzstonlex.jnq.impl.field.ValueDataField;
import com.itzstonlex.jnq.request.query.RequestQuery;
import com.itzstonlex.jnq.request.query.session.RequestSessionAppender;
import lombok.NonNull;

public interface RequestInsert extends RequestQuery {

    @NonNull
    RequestInsert withIgnored();

    @NonNull
    RequestInsert withUpdateDuplicatedKeys();

    @NonNull
    RequestSessionAppender<ValueDataField, RequestInsert> session();
}
