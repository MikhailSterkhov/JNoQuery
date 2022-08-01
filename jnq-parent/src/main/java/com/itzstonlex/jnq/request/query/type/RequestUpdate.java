package com.itzstonlex.jnq.request.query.type;

import com.itzstonlex.jnq.request.query.RequestQuery;
import com.itzstonlex.jnq.request.query.session.RequestSessionFilter;
import lombok.NonNull;

public interface RequestUpdate extends RequestQuery {

    @NonNull
    RequestSessionFilter<RequestUpdate> sessionUpdater();

    @NonNull
    RequestSessionFilter<RequestUpdate> sessionFilter();
}
