package com.itzstonlex.jnq.orm.base.request;

import com.itzstonlex.jnq.content.type.SchemaContent;
import com.itzstonlex.jnq.orm.ObjectMapper;
import com.itzstonlex.jnq.orm.data.ObjectMappingService;
import com.itzstonlex.jnq.orm.exception.JnqObjectMappingException;
import com.itzstonlex.jnq.orm.mapper.AutomaticallyMapper;
import com.itzstonlex.jnq.orm.request.MappingRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public abstract class AbstractMappingRequest implements MappingRequest {

    SchemaContent schema;
    String table;

    ObjectMappingService objectMappingService;

    @NonFinal
    ObjectMapper<Object> mapper;

    @SuppressWarnings("unchecked")
    @Override
    public @NonNull MappingRequest markMapper(@NonNull ObjectMapper<?> objectMapper) {
        this.mapper = (ObjectMapper<Object>) objectMapper;
        return this;
    }

    @Override
    public @NonNull <T extends ObjectMapper<T>> MappingRequest markMapper(@NonNull Class<T> cls) throws JnqObjectMappingException {
        return markMapper(objectMappingService.findMapper(cls));
    }

    @Override
    public @NonNull MappingRequest markAutomapping() {
        return markMapper(new AutomaticallyMapper(objectMappingService));
    }

}
