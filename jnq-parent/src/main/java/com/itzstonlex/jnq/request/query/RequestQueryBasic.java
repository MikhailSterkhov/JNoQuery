package com.itzstonlex.jnq.request.query;

import com.itzstonlex.jnq.field.DataField;
import com.itzstonlex.jnq.request.query.session.RequestSessionAppender;
import com.itzstonlex.jnq.request.query.session.RequestSessionFilter;
import com.itzstonlex.jnq.request.query.session.RequestSessionJoiner;
import lombok.NonNull;

public interface RequestQueryBasic<F extends DataField> extends RequestQuery {

    @NonNull
    RequestSessionAppender<F, RequestQueryBasic<F>> sessionAppender();

    @NonNull
    RequestSessionFilter<RequestQueryBasic<F>> sessionFilter();

    @NonNull
    RequestSessionJoiner<RequestQueryBasic<F>> sessionJoiner();
}
