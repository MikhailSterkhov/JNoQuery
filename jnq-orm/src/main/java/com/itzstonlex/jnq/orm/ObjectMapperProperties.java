package com.itzstonlex.jnq.orm;

import lombok.NonNull;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public interface ObjectMapperProperties {

    void foreach(@NonNull BiConsumer<String, Object> foreach);

    @NonNull
    ObjectMapperProperties set(@NonNull String key, Object value);

    @NonNull
    ObjectMapperProperties remove(@NonNull String key);

    @NonNull
    <T> T get(@NonNull String key, @NonNull Supplier<T> defaultValue);
}
