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
    Request with(@NonNull RequestConcurrency concurrency);

    @NonNull
    Request with(@NonNull RequestFetchDirection fetchDirection);

    @NonNull
    Request with(@NonNull RequestHoldability holdability);

    @NonNull
    Request with(@NonNull RequestType type);

    @NonNull
    RequestFactory factory();
}
