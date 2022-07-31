package com.itzstonlex.jnq.request.query;

import com.itzstonlex.jnq.request.RequestExecutor;
import lombok.NonNull;

public interface RequestQueryType {

    @NonNull RequestExecutor complete();
}
