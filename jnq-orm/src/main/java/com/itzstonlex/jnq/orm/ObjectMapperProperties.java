package com.itzstonlex.jnq.orm;

import lombok.NonNull;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public interface ObjectMapperProperties {

    void foreach(@NonNull BiConsumer<String, Object> foreach);

    void removeAll();

    @NonNull
    ObjectMapperProperties set(@NonNull String key, Object value);

    @NonNull
    ObjectMapperProperties remove(@NonNull String key);

    @NonNull
    <T> T peek(@NonNull String key, Supplier<T> defaultValue);

    default <T> T peek(@NonNull String key) {
        return peek(key, () -> null);
    }

    default <T> T poll(@NonNull String key, Supplier<T> defaultValue) {
        T obj = peek(key, defaultValue);

        remove(key);
        return obj;
    }

    default <T> T poll(@NonNull String key) {
        return poll(key, () -> null);
    }
}
