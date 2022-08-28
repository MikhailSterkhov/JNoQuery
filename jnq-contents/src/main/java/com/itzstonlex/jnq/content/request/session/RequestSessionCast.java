package com.itzstonlex.jnq.content.request.session;

import com.itzstonlex.jnq.content.request.RequestQuery;
import lombok.NonNull;

public interface RequestSessionCast<Session extends RequestSessionSelector<Query>, Query extends RequestQuery> extends RequestSession<Query> {

    @NonNull
    Session as(@NonNull String cast);

    @NonNull
    Session uncheck();
}
