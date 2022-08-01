package com.itzstonlex.jnq.request.query.session;

import com.itzstonlex.jnq.request.query.RequestQuery;
import lombok.NonNull;

public interface RequestSession<Query extends RequestQuery> {

    @NonNull
    Query backward();
}
