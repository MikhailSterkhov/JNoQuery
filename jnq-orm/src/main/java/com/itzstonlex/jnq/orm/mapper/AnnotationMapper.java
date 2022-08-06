package com.itzstonlex.jnq.orm.mapper;

import com.itzstonlex.jnq.orm.ObjectMapper;
import com.itzstonlex.jnq.orm.ObjectMapperProperties;
import com.itzstonlex.jnq.orm.annotation.Mapping;
import com.itzstonlex.jnq.orm.annotation.MappingColumn;
import com.itzstonlex.jnq.orm.annotation.MappingInitMethod;
import com.itzstonlex.jnq.orm.exception.JnqObjectMappingException;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class AnnotationMapper<T> implements ObjectMapper<T> {

    @NonNull
    private Map<String, Field> _toDeclaredMappingFields(Class<?> cls) {
        Map<String, Field> map = new HashMap<>();

        Field[] declaredFields = cls.getDeclaredFields();

        for (Field field : declaredFields) {
            String name = field.getName();

            MappingColumn mappingColumn = field.getDeclaredAnnotation(MappingColumn.class);

            if (mappingColumn != null && !mappingColumn.value().isEmpty()) {
                name = mappingColumn.value();
            }

            map.put(name, field);
        }

        return map;
    }

    @NonNull
    private Set<Method> getInitMethods(Object src) {
        Set<Method> methodSet = new HashSet<>();

        for (Method method : src.getClass().getDeclaredMethods()) {
            if (method.getParameterCount() != 0) {
                continue;
            }

            if (method.isAnnotationPresent(MappingInitMethod.class)) {

                method.setAccessible(true);
                methodSet.add(method);
            }
        }

        return methodSet;
    }

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

        for (Map.Entry<String, Field> entry : _toDeclaredMappingFields(src.getClass()).entrySet()) {

            String name = entry.getKey();
            Field field = entry.getValue();

            try {
                field.setAccessible(true);
                properties.set(name, field.get(src));
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
            T instance = constructor.newInstance();

            for (Map.Entry<String, Field> entry : _toDeclaredMappingFields(cls).entrySet()) {

                String name = entry.getKey();
                Field field = entry.getValue();

                Object value = properties.of(name.toLowerCase(), () -> null);

                field.setAccessible(true);
                field.set(instance, value);
            }

            for (Method initMethod : getInitMethods(instance)) {
                initMethod.invoke(instance);
            }

            return instance;
        }
        catch (Exception exception) {
            throw new JnqObjectMappingException("fetch", exception);
        }
    }

}
