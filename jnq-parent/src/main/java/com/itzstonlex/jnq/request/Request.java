package com.itzstonlex.jnq.request;

import com.itzstonlex.jnq.DataConnection;
import com.itzstonlex.jnq.request.option.RequestConcurrency;
import com.itzstonlex.jnq.request.option.RequestFetchDirection;
import com.itzstonlex.jnq.request.option.RequestHoldability;
import com.itzstonlex.jnq.request.option.RequestType;
import lombok.NonNull;

public interface Request {

    @NonNull
    DataConnection connection();

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
    RequestFactory factory();
}
