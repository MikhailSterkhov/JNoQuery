package com.itzstonlex.jnq.request.query.type;

import com.itzstonlex.jnq.request.query.RequestQuery;
import com.itzstonlex.jnq.request.query.session.*;
import lombok.NonNull;

public interface RequestFinder extends RequestQuery {

    @NonNull
    RequestFinder withLimit(int limit);

    @NonNull
    RequestSessionSelector<RequestFinder> sessionSelector();

    @NonNull
    RequestSessionSortBy<RequestFinder> sessionSort();

    @NonNull
    RequestSessionGroupBy<RequestFinder> sessionGroup();

    @NonNull
    RequestSessionFilter<RequestFinder> sessionFilter();

    @NonNull
    RequestSessionJoiner<RequestFinder> sessionJoiner();
}
