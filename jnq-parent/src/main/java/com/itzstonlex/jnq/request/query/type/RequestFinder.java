package com.itzstonlex.jnq.request.query.type;

import com.itzstonlex.jnq.request.query.RequestQuery;
import com.itzstonlex.jnq.request.query.session.RequestSessionFilter;
import com.itzstonlex.jnq.request.query.session.RequestSessionJoiner;
import com.itzstonlex.jnq.request.query.session.RequestSessionSelector;
import lombok.NonNull;

public interface RequestFinder extends RequestQuery {

    @NonNull
    RequestSessionSelector<RequestFinder> sessionSelector();

    @NonNull
    RequestSessionFilter<RequestFinder> sessionOrder();

    @NonNull
    RequestSessionFilter<RequestFinder> sessionGroup();

    @NonNull
    RequestSessionFilter<RequestFinder> sessionFilter();

    @NonNull
    RequestSessionJoiner<RequestFinder> sessionJoiner();
}
