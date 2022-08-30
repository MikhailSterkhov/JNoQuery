package com.itzstonlex.jnq.orm.mapper;

import com.itzstonlex.jnq.orm.ObjectMapper;
import com.itzstonlex.jnq.orm.ObjectMapperProperties;
import com.itzstonlex.jnq.orm.annotation.*;
import com.itzstonlex.jnq.orm.exception.JnqObjectMappingException;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.TimeUnit;

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

    @NonNull
    private Set<Field> getPrimaryFields(Object src)
    throws JnqObjectMappingException {

        Set<Field> primarySet = new HashSet<>();

        for (Field field : src.getClass().getDeclaredFields()) {

            if (field.isAnnotationPresent(MappingPrimary.class)) {

                if (!field.getType().isPrimitive()) {
                    throw new JnqObjectMappingException("primary key field type must be primitive");
                }

                primarySet.add(field);
            }
        }

        return primarySet;
    }

    protected void validateSourceType(@NonNull Class<?> cls)
    throws JnqObjectMappingException {

        if (!cls.isAnnotationPresent(MappingEntity.class)) {
            throw new JnqObjectMappingException("No @Mapping annotation found in `%s`", cls.getName());
        }
    }

    @Override
    public void serialize(@NonNull T src, @NonNull ObjectMapperProperties properties)
    throws JnqObjectMappingException {

        this.validateSourceType(src.getClass());

        Set<Field> primaryFieldsSet = getPrimaryFields(src);
        Set<String> primaryFieldsNamesSet = new HashSet<>();

        String identifierFieldName = null;

        for (Map.Entry<String, Field> entry : _toDeclaredMappingFields(src.getClass()).entrySet()) {

            String name = entry.getKey();
            Field field = entry.getValue();

            field.setAccessible(true);

            String fieldName = field.getName();
            Class<?> fieldType = field.getType();

            if (field.isAnnotationPresent(MappingLastUpdateTime.class)) {
                boolean isLong = fieldType.isAssignableFrom(long.class) || fieldType.isAssignableFrom(Long.class);
                boolean isDate = fieldType.isAssignableFrom(Date.class);

                if (!isLong && !isDate) {
                    throw new JnqObjectMappingException("Entity LastUpdateTime field type must have equals 'long' or 'Long' or `Date`");
                }

                try {
                    MappingLastUpdateTime annotation = field.getDeclaredAnnotation(MappingLastUpdateTime.class);
                    long time = annotation.unit().convert(System.currentTimeMillis(), TimeUnit.MILLISECONDS);

                    field.set(src, isLong ? time : new Date(TimeUnit.MILLISECONDS.convert(time, annotation.unit())));
                }
                catch (Exception exception) {
                    throw new JnqObjectMappingException("serialize", exception);
                }
            }

            if (field.isAnnotationPresent(MappingID.class)) {
                if (identifierFieldName != null) {
                    throw new JnqObjectMappingException("Entity cannot be use > 1 identifiers");
                }

                if (!fieldType.isAssignableFrom(int.class) && !fieldType.isAssignableFrom(Integer.class)) {
                    throw new JnqObjectMappingException("Entity ID field type must have equals 'int' or 'Integer'");
                }

                identifierFieldName = fieldName;
            }

            try {
                properties.set(name, field.get(src));

                if (primaryFieldsSet.stream().anyMatch(f -> f.getName().equalsIgnoreCase(fieldName))) {
                    primaryFieldsNamesSet.add(name.toLowerCase());
                }
            }
            catch (Exception exception) {
                throw new JnqObjectMappingException("serialize", exception);
            }
        }

        if (identifierFieldName == null) {
            throw new JnqObjectMappingException("Entity ID is not found! (by @MappingID)");
        }

        properties.set(MappingID.PROPERTY_KEY_NAME, identifierFieldName);
        properties.set(MappingPrimary.PROPERTY_KEY_NAME, primaryFieldsNamesSet);
    }

    @Override
    public @NonNull T deserialize(@NonNull Class<T> cls, @NonNull ObjectMapperProperties properties)
    throws JnqObjectMappingException {

        this.validateSourceType(cls);

        try {
            Constructor<T> constructor = cls.getDeclaredConstructor();
            constructor.setAccessible(true);

            T instance = constructor.newInstance();

            for (Map.Entry<String, Field> entry : _toDeclaredMappingFields(cls).entrySet()) {

                String name = entry.getKey();
                Field field = entry.getValue();

                Object value = properties.peek(name.toLowerCase());

                field.setAccessible(true);
                field.set(instance, value);
            }

            for (Method initMethod : getInitMethods(instance)) {
                initMethod.invoke(instance);
            }

            return instance;
        }
        catch (Exception exception) {
            throw new JnqObjectMappingException("deserialize", exception);
        }
    }

}
