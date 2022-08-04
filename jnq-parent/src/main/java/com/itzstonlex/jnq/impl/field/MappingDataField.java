package com.itzstonlex.jnq.impl.field;

import com.itzstonlex.jnq.field.FieldOperator;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MappingDataField extends ValueDataField {

    public static @NonNull MappingDataField create(@NonNull FieldOperator operator, @NonNull String name, Object value) {
        return new MappingDataField(operator, name, value);
    }

    public static @NonNull MappingDataField create(@NonNull String name, Object value) {
        return new MappingDataField(name, value);
    }

    public static @NonNull MappingDataField create(@NonNull String name) {
        return new MappingDataField(name, null);
    }

    FieldOperator operator;

    protected MappingDataField(@NonNull FieldOperator operator, @NonNull String name, Object value) {
        super(name, value);
        this.operator = operator;
    }

    protected MappingDataField(@NonNull String name, Object value) {
        this(FieldOperator.EQUAL, name, value);
    }

    protected MappingDataField(@NonNull String name) {
        this(name, null);
    }

    public FieldOperator operator() {
        return operator;
    }
}
