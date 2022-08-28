package com.itzstonlex.jnq.content.request;

import com.itzstonlex.jnq.content.RequestExecutor;
import lombok.NonNull;

public interface RequestQuery {

    @NonNull
    RequestExecutor compile();
}
