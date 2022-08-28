package com.itzstonlex.jnq.content.request.type;

import com.itzstonlex.jnq.content.request.session.RequestSessionCondition;
import com.itzstonlex.jnq.content.request.RequestQuery;
import lombok.NonNull;

public interface RequestDelete extends RequestQuery {

    @NonNull
    RequestSessionCondition<RequestDelete> beginCondition();
}
