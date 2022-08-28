package com.itzstonlex.jnq.content.request.type;

import com.itzstonlex.jnq.content.request.RequestQuery;
import lombok.NonNull;

public interface RequestCreateSchema extends RequestQuery {

    @NonNull
    RequestCreateSchema checkAvailability();
}
