package com.itzstonlex.jnq.content.field.type;

import com.itzstonlex.jnq.content.field.DataField;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EntryField implements DataField {

    public static @NonNull EntryField create(@NonNull String name, Object value) {
        return new EntryField(name, value);
    }

    public static @NonNull EntryField create(@NonNull String name) {
        return new EntryField(name, null);
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