package com.itzstonlex.jnq.impl.field;

import com.itzstonlex.jnq.field.DataField;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ValueDataField implements DataField {

    public static @NonNull ValueDataField create(@NonNull String name, Object value) {
        return new ValueDataField(name, value);
    }

    public static @NonNull ValueDataField create(@NonNull String name) {
        return new ValueDataField(name, null);
    }

    String name;

    @NonFinal
    Object value;

    @Override
    public @NonNull String name() {
        return name;
    }

    public Object value() {
        return value;
    }

    public void set(Object value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return ("`" + name + "`" + (value == null ? " is NULL" : " = " + (value instanceof Number ? value : "'" + value + "'")));
    }
}