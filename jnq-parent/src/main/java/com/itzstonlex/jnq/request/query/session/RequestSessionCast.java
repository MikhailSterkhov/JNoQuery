package com.itzstonlex.jnq.request.query.session;

import com.itzstonlex.jnq.request.query.RequestQuery;
import lombok.NonNull;

public interface RequestSessionCast<Session extends RequestSessionSelector<Query>, Query extends RequestQuery> extends RequestSession<Query> {

    @NonNull
    Session as(@NonNull String cast);

    @NonNull
    Session uncheck();
}
