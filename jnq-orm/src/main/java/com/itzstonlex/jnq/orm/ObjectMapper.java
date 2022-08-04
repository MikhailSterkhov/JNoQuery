package com.itzstonlex.jnq.orm;

import com.itzstonlex.jnq.orm.exception.JnqObjectMappingException;
import lombok.NonNull;

public interface ObjectMapper<T> {

    @NonNull
    T fetch(@NonNull Class<T> cls, @NonNull ObjectMapperProperties properties) throws JnqObjectMappingException;

    void mapping(@NonNull T src, @NonNull ObjectMapperProperties properties) throws JnqObjectMappingException;
}
