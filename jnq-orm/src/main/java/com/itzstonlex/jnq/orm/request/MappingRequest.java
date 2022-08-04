package com.itzstonlex.jnq.orm.request;

import com.itzstonlex.jnq.orm.ObjectMapper;
import com.itzstonlex.jnq.orm.exception.JnqObjectMappingException;
import lombok.NonNull;

public interface MappingRequest {

    @NonNull
    MappingRequest withMapper(@NonNull ObjectMapper<?> objectMapper);

    @NonNull
    MappingRequest withMapper(@NonNull Class<? extends ObjectMapper<?>> cls) throws JnqObjectMappingException;

    @NonNull
    MappingRequest withAutomapping();

    @NonNull
    MappingRequestExecutor compile();
}
