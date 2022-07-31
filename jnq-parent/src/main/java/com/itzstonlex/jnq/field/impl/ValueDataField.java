package com.itzstonlex.jnq.field.impl;

import com.itzstonlex.jnq.field.DataField;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ValueDataField implements DataField {

    public static ValueDataField create(String name, Object value) {
        return new ValueDataField(name, value);
    }

    public static ValueDataField create(String name) {
        return new ValueDataField(name, null);
    }

    private final String name;
    private Object value;

    @Override
    public String name() {
        return name;
    }

    public Object value() {
        return value;
    }

    public void set(Object value) {
        this.value = value;
    }

}