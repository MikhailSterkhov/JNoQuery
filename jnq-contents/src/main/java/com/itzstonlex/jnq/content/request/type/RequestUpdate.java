package com.itzstonlex.jnq.content.request.type;

import com.itzstonlex.jnq.content.request.RequestQuery;
import com.itzstonlex.jnq.content.request.session.RequestSessionCondition;
import lombok.NonNull;

public interface RequestUpdate extends RequestQuery {

    @NonNull
    RequestSessionCondition<RequestUpdate> beginUpdateCondition();

    @NonNull
    RequestSessionCondition<RequestUpdate> beginCondition();
}
