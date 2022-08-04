package com.itzstonlex.jnq.orm.mapper;

import com.google.gson.Gson;
import com.itzstonlex.jnq.orm.ObjectMapper;
import com.itzstonlex.jnq.orm.ObjectMapperProperties;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JsonMapper<T> implements ObjectMapper<T> {

    private static final Gson GSON = new Gson();

    @NonFinal
    @Setter
    String columnName = "json";

    @Override
    public void mapping(@NonNull T src, @NonNull ObjectMapperProperties properties) {
        properties.set(columnName, GSON.toJson(src));
    }

    @Override
    public @NonNull T fetch(@NonNull Class<T> cls, @NonNull ObjectMapperProperties properties) {
        return GSON.fromJson(properties.get(columnName, () -> ""), cls);
    }

}
