package com.itzstonlex.jnq.orm.mapper;

import com.itzstonlex.jnq.orm.ObjectMapper;
import com.itzstonlex.jnq.orm.ObjectMapperProperties;
import com.itzstonlex.jnq.orm.ObjectMappingService;
import com.itzstonlex.jnq.orm.exception.JnqObjectMappingException;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class AutomaticallyMapper implements ObjectMapper<Object> {

    ObjectMappingService<?> objectMappingService;

    @Override
    public @NonNull Object fetch(@NonNull Class<Object> cls, @NonNull ObjectMapperProperties properties)
    throws JnqObjectMappingException {

        return this._findMapper(cls).fetch(cls, properties);
    }

    @Override
    public void mapping(@NonNull Object src, @NonNull ObjectMapperProperties properties)
    throws JnqObjectMappingException {

        this._findMapper(src.getClass()).mapping(src, properties);
    }

    private ObjectMapper<Object> _findMapper(Class<?> cls)
    throws JnqObjectMappingException {

        try {
            return objectMappingService.findMapperByType(cls);
        }
        catch (JnqObjectMappingException exception) {
            throw new JnqObjectMappingException(exception);
        }
    }

}
