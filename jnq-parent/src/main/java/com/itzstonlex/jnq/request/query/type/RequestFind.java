package com.itzstonlex.jnq.request.query.type;

import com.itzstonlex.jnq.request.query.RequestQuery;
import com.itzstonlex.jnq.request.query.session.RequestSessionFilter;
import com.itzstonlex.jnq.request.query.session.RequestSessionJoiner;
import com.itzstonlex.jnq.request.query.session.RequestSessionSelector;
import lombok.NonNull;

public interface RequestFind extends RequestQuery {

    @NonNull
    RequestSessionSelector<RequestFind> sessionSelector();

    @NonNull
    RequestSessionFilter<RequestFind> sessionOrder();

    @NonNull
    RequestSessionFilter<RequestFind> sessionGroup();

    @NonNull
    RequestSessionFilter<RequestFind> sessionFilter();

    @NonNull
    RequestSessionJoiner<RequestFind> sessionJoiner();
}
