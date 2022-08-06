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
    <T> T of(@NonNull String key, @NonNull Supplier<T> defaultValue);

    default <T> T ofNullable(@NonNull String key) {
        return of(key, () -> null);
    }
}
