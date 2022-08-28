package com.itzstonlex.jnq.content.request.session;

import com.itzstonlex.jnq.content.request.RequestQuery;
import lombok.NonNull;

public interface RequestSession<Query extends RequestQuery> {

    @NonNull
    Query endpoint();
}
