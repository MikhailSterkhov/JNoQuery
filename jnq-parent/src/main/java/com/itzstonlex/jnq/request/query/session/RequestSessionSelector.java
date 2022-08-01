package com.itzstonlex.jnq.request.query.session;

import com.itzstonlex.jnq.request.query.RequestQuery;
import lombok.NonNull;

public interface RequestSessionSelector<Query extends RequestQuery> extends RequestSession<Query>  {

    @NonNull
    Query withAll();

    @NonNull
    RequestSessionSelector<Query> with(@NonNull String field);

    @NonNull
    RequestSessionSelector<Query> withCount(@NonNull String field);

    @NonNull
    RequestSessionSelector<Query> withLowerCase(@NonNull String field);

    @NonNull
    RequestSessionSelector<Query> withUpperCase(@NonNull String field);
}
