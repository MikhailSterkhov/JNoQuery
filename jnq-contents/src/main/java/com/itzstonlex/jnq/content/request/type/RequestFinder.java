package com.itzstonlex.jnq.content.request.type;

import com.itzstonlex.jnq.content.request.RequestQuery;
import com.itzstonlex.jnq.content.request.session.*;
import lombok.NonNull;

public interface RequestFinder extends RequestQuery {

    @NonNull
    RequestFinder markLimit(int limit);

    @NonNull
    RequestSessionSelector<RequestFinder> beginSelection();

    @NonNull
    RequestSessionSorting<RequestFinder> beginSorting();

    @NonNull
    RequestSessionGrouping<RequestFinder> beginGrouping();

    @NonNull
    RequestSessionCondition<RequestFinder> beginCondition();

    @NonNull
    RequestSessionJoiner<RequestFinder> beginJoiner();
}
