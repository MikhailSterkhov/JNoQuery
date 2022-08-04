package com.itzstonlex.jnq.impl.orm.properties;

import com.itzstonlex.jnq.orm.ObjectMapperProperties;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MapObjectMapperProperties implements ObjectMapperProperties {

    Map<String, Object> propertiesMap = new LinkedHashMap<>();

    @Override
    public @NonNull ObjectMapperProperties set(@NonNull String key, Object value) {
        propertiesMap.put(key, value);
        return this;
    }

    @Override
    public @NonNull ObjectMapperProperties remove(@NonNull String key) {
        propertiesMap.remove(key);
        return this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> @NonNull T get(@NonNull String key, @NonNull Supplier<T> defaultValue) {
        T returnValue = (T) propertiesMap.get(key);

        if (returnValue == null) {
            return defaultValue.get();
        }

        return returnValue;
    }

    @Override
    public void foreach(@NonNull BiConsumer<String, Object> foreach) {
        propertiesMap.forEach(foreach);
    }

}
