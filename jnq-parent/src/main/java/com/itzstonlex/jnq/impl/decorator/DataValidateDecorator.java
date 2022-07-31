package com.itzstonlex.jnq.impl.decorator;

import com.itzstonlex.jnq.DataValidator;
import lombok.NonNull;

import java.util.function.Function;

public class DataValidateDecorator implements DataValidator {

    protected final boolean _validateNumber(String string, Function<String, Number> validation) {
        try {
            validation.apply(string);
            return true;
        }
        catch (NumberFormatException exception) {
            return false;
        }
    }

    @Override
    public boolean isString(@NonNull Object value) {
        return (value instanceof String);
    }

    @Override
    public boolean isLong(@NonNull Object value) {
        return this._validateNumber(value.toString(), Long::parseLong);
    }

    @Override
    public boolean isInt(@NonNull Object value) {
        return this._validateNumber(value.toString(), Integer::parseInt);
    }

    @Override
    public boolean isDouble(@NonNull Object value) {
        return this._validateNumber(value.toString(), Double::parseDouble);
    }

    @Override
    public boolean isFloat(@NonNull Object value) {
        return this._validateNumber(value.toString(), Float::parseFloat);
    }

    @Override
    public boolean isShort(@NonNull Object value) {
        return this._validateNumber(value.toString(), Short::parseShort);
    }

    @Override
    public boolean isByte(@NonNull Object value) {
        return this._validateNumber(value.toString(), Byte::parseByte);
    }
}
