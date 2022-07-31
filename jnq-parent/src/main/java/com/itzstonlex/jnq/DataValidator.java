package com.itzstonlex.jnq;

import lombok.NonNull;

public interface DataValidator {

    default boolean isPrimitive(@NonNull Object value) {
        return isString(value) || isLong(value) || isInt(value) || isDouble(value) || isFloat(value) || isShort(value) || isByte(value);
    }

    boolean isString(@NonNull Object value);

    boolean isLong(@NonNull Object value);

    boolean isInt(@NonNull Object value);

    boolean isDouble(@NonNull Object value);

    boolean isFloat(@NonNull Object value);

    boolean isShort(@NonNull Object value);

    boolean isByte(@NonNull Object value);
}
