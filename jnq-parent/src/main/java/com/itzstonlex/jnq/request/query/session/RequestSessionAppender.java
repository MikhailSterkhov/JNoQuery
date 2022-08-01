package com.itzstonlex.jnq.request.query.session;

import com.itzstonlex.jnq.field.DataField;
import com.itzstonlex.jnq.request.query.RequestQuery;
import lombok.NonNull;

public interface RequestSessionAppender<F extends DataField, Query extends RequestQuery> extends RequestSession<Query> {

    @NonNull
    RequestSessionAppender<F, Query> append(@NonNull F field);
}
