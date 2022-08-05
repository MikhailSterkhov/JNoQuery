package com.itzstonlex.jnq.impl.orm.request;

import com.itzstonlex.jnq.DataConnection;
import com.itzstonlex.jnq.impl.field.MappingDataField;
import com.itzstonlex.jnq.orm.ObjectMapper;
import com.itzstonlex.jnq.orm.ObjectMappingService;
import com.itzstonlex.jnq.orm.exception.JnqObjectMappingException;
import com.itzstonlex.jnq.orm.mapper.AutomaticallyMapper;
import com.itzstonlex.jnq.orm.request.MappingRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

@RequiredArgsConstructor
@Getter(AccessLevel.PROTECTED)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public abstract class AbstractMappingRequest implements MappingRequest {

    DataConnection connection;
    String schema, table;

    ObjectMappingService<MappingDataField> objectMappingService;

    @NonFinal
    ObjectMapper<Object> mapper;

    @SuppressWarnings("unchecked")
    @Override
    public @NonNull MappingRequest withMapper(@NonNull ObjectMapper<?> objectMapper) {
        this.mapper = (ObjectMapper<Object>) objectMapper;
        return this;
    }

    @Override
    public @NonNull MappingRequest withMapper(@NonNull Class<? extends ObjectMapper<?>> cls) throws JnqObjectMappingException {
        return withMapper(objectMappingService.findMapper(cls));
    }

    @Override
    public @NonNull MappingRequest withAutomapping() {
        return withMapper(new AutomaticallyMapper(objectMappingService));
    }

}
