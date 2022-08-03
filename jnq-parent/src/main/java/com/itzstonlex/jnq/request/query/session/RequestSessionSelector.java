package com.itzstonlex.jnq.request.query.session;

import com.itzstonlex.jnq.request.query.RequestQuery;
import lombok.NonNull;

public interface RequestSessionSelector<Query extends RequestQuery> extends RequestSession<Query>  {

    @NonNull
    Query withAll();

    @NonNull
    RequestSessionSelector<Query> with(@NonNull String field);

    @NonNull
    RequestSessionCast<RequestSessionSelector<Query>, Query> withCasted(@NonNull String field);

    @NonNull
    RequestSessionCast<RequestSessionSelector<Query>, Query> withQuery(@NonNull RequestQuery query);

    @NonNull
    RequestSessionCast<RequestSessionSelector<Query>, Query> withCount(@NonNull String field);

    @NonNull
    RequestSessionCast<RequestSessionSelector<Query>, Query> withLowerCase(@NonNull String field);

    @NonNull
    RequestSessionCast<RequestSessionSelector<Query>, Query> withUpperCase(@NonNull String field);

    @NonNull
    RequestSessionCast<RequestSessionSelector<Query>, Query> withMax(@NonNull String field);

    @NonNull
    RequestSessionCast<RequestSessionSelector<Query>, Query> withMin(@NonNull String field);

    @NonNull
    RequestSessionCast<RequestSessionSelector<Query>, Query> withAvg(@NonNull String field);
}
