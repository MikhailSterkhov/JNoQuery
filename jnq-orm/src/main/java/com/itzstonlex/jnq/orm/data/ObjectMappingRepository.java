package com.itzstonlex.jnq.orm.data;

import com.itzstonlex.jnq.orm.exception.JnqObjectMappingException;
import com.itzstonlex.jnq.orm.request.MappingRequestFactory;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public abstract class ObjectMappingRepository {

    MappingRequestFactory requestFactory;

    public CompletableFuture<Integer> save(Object object)
    throws JnqObjectMappingException {
        return requestFactory.newUpdate().markAutomapping().compile().save(object);
    }

    // todo - add fetchers.
}
