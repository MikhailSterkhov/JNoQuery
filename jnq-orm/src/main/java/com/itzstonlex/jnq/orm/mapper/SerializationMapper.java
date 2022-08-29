package com.itzstonlex.jnq.orm.mapper;

import com.itzstonlex.jnq.orm.ObjectMapper;
import com.itzstonlex.jnq.orm.ObjectMapperProperties;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.SerializationUtils;

import java.io.Serializable;
import java.util.function.Supplier;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class SerializationMapper<T extends Serializable> implements ObjectMapper<T> {

    private static final Supplier<byte[]> DEFAULT_DATA_FACTORY = () -> new byte[512];

    @Override
    public void serialize(@NonNull T src, @NonNull ObjectMapperProperties properties) {
        properties.set("data", SerializationUtils.serialize(src));
    }

    @Override
    public @NonNull T deserialize(@NonNull Class<T> cls, @NonNull ObjectMapperProperties properties) {
        return SerializationUtils.deserialize(properties.peek("data", DEFAULT_DATA_FACTORY));
    }

}
