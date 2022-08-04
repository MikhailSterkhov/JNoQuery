package com.itzstonlex.jnq.orm.mapper;

import com.itzstonlex.jnq.orm.ObjectMapper;
import com.itzstonlex.jnq.orm.ObjectMapperProperties;
import com.itzstonlex.jnq.orm.annotation.Mapping;
import com.itzstonlex.jnq.orm.annotation.MappingColumn;
import com.itzstonlex.jnq.orm.exception.JnqObjectMappingException;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class AnnotationMapper<T> implements ObjectMapper<T> {

    protected void validateSourceType(@NonNull Class<?> cls)
    throws JnqObjectMappingException {

        if (!cls.isAnnotationPresent(Mapping.class)) {
            throw new JnqObjectMappingException("No @Mapping annotation found in `%s`", cls.getName());
        }
    }

    @Override
    public void mapping(@NonNull T src, @NonNull ObjectMapperProperties properties)
    throws JnqObjectMappingException {

        this.validateSourceType(src.getClass());

        Class<?> cls = src.getClass();
        Field[] declaredFields = cls.getDeclaredFields();

        for (Field field : declaredFields) {
            String name = field.getName();

            MappingColumn mappingColumn = field.getDeclaredAnnotation(MappingColumn.class);

            if (mappingColumn != null && !mappingColumn.value().isEmpty()) {
                name = mappingColumn.value();
            }

            try {
                field.setAccessible(true);
                properties.set(name, field.get(src));

                field.setAccessible(false);
            }
            catch (Exception exception) {
                throw new JnqObjectMappingException("mapping", exception);
            }
        }
    }

    @Override
    public @NonNull T fetch(@NonNull Class<T> cls, @NonNull ObjectMapperProperties properties)
    throws JnqObjectMappingException {

        this.validateSourceType(cls);

        try {
            Constructor<T> constructor = cls.getConstructor();

            T obj = constructor.newInstance();

            for (Field field : cls.getDeclaredFields()) {
                String name = field.getName();

                MappingColumn mappingColumn = field.getDeclaredAnnotation(MappingColumn.class);

                if (mappingColumn != null && !mappingColumn.value().isEmpty()) {
                    name = mappingColumn.value();
                }

                Object value = properties.get(name.toLowerCase(), () -> null);

                field.setAccessible(true);
                field.set(obj, value);

                field.setAccessible(false);
            }

            return obj;
        }
        catch (Exception exception) {
            throw new JnqObjectMappingException("fetch", exception);
        }
    }

}
