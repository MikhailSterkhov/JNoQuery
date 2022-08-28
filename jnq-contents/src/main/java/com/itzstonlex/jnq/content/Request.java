package com.itzstonlex.jnq.content;

import com.itzstonlex.jnq.content.request.RequestConcurrency;
import com.itzstonlex.jnq.content.request.RequestFetchDirection;
import com.itzstonlex.jnq.content.request.RequestHoldability;
import com.itzstonlex.jnq.content.request.RequestType;
import lombok.NonNull;

public interface Request {

    RequestConcurrency concurrency();

    RequestFetchDirection fetchDirection();

    RequestHoldability holdability();

    RequestType type();

    @NonNull
    Request set(@NonNull RequestConcurrency concurrency);

    @NonNull
    Request set(@NonNull RequestFetchDirection fetchDirection);

    @NonNull
    Request set(@NonNull RequestHoldability holdability);

    @NonNull
    Request set(@NonNull RequestType type);

    @NonNull
    RequestFactory toFactory();
}
