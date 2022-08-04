package com.itzstonlex.jnq.orm;

import com.itzstonlex.jnq.orm.exception.JnqObjectMappingException;
import com.itzstonlex.jnq.orm.request.MappingRequestFactory;
import lombok.NonNull;

import java.util.Map;

public interface ObjectMappingService<RequestInclude> {

    MappingRequestFactory<RequestInclude> getRequestFactory(@NonNull String schema, @NonNull String table);

    MappingRequestFactory<RequestInclude> getRequestFactory(@NonNull String table);

    Map<String, Object> toMap(@NonNull Object object) throws JnqObjectMappingException;

    Map<String, Class<?>> toMap(@NonNull Class<?> cls) throws JnqObjectMappingException;

    <T> ObjectMapper<T> findRequiredMapperByType(@NonNull Class<? extends T> cls) throws JnqObjectMappingException;

    <T> ObjectMapper<T> findMapperByType(@NonNull Class<? extends T> cls) throws JnqObjectMappingException;

    <T extends ObjectMapper<?>> T findMapper(@NonNull Class<T> mapperCls) throws JnqObjectMappingException;

    <T> void register(@NonNull Class<T> cls, @NonNull ObjectMapper<T> mapper) throws JnqObjectMappingException;

    void unregister(@NonNull Class<?> cls);

    void unregisterAll(@NonNull Class<? extends ObjectMapper<?>> mapperCls);
}
