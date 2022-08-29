package com.itzstonlex.jnq.orm.request;

import com.itzstonlex.jnq.orm.ObjectMappingRepository;
import com.itzstonlex.jnq.orm.ObjectMapper;
import com.itzstonlex.jnq.orm.exception.JnqObjectMappingException;
import lombok.NonNull;

public interface MappingRequest {

    @NonNull
    MappingRequest markMapper(@NonNull ObjectMapper<?> objectMapper);

    @NonNull
    <T extends ObjectMapper<T>> MappingRequest markMapper(@NonNull Class<T> cls) throws JnqObjectMappingException;

    @NonNull
    MappingRequest markAutomapping();

    @NonNull
    ObjectMappingRepository compile();
}
