package com.itzstonlex.jnq.request.query;

import com.itzstonlex.jnq.exception.JnqException;
import com.itzstonlex.jnq.request.RequestExecutor;
import lombok.NonNull;

public interface RequestQuery {

    @NonNull
    RequestExecutor compile();
}
