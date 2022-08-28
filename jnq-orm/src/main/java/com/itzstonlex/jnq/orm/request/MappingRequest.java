package com.itzstonlex.jnq.orm.request;

import com.itzstonlex.jnq.orm.ObjectMapper;
import com.itzstonlex.jnq.orm.exception.JnqObjectMappingException;
import lombok.NonNull;

public interface MappingRequest {

    @NonNull
    MappingRequest mapper(@NonNull ObjectMapper<?> objectMapper);

    @NonNull
    <T extends ObjectMapper<T>> MappingRequest mapper(@NonNull Class<T> cls) throws JnqObjectMappingException;

    @NonNull
    MappingRequest markAutomapping();

    @NonNull
    MappingRequestExecutor compile();
}
