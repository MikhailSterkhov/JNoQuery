package com.itzstonlex.jnq.request.query.type;

import com.itzstonlex.jnq.request.query.RequestQuery;
import lombok.NonNull;

public interface RequestCreateScheme extends RequestQuery {

    @NonNull
    RequestCreateScheme withExistsChecking();
}
