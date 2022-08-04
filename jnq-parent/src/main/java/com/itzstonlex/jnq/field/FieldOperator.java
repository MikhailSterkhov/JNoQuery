package com.itzstonlex.jnq.field;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

import java.util.function.Function;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum FieldOperator {

    LIKE("LIKE", ((value) -> "%" + value + "%")),

    EQUAL("="),

    MORE(">"),

    LESS("<"),

    MORE_OR_EQUAL(">="),

    LESS_OR_EQUAL("<="),
    ;

    String sql;

    @SuppressWarnings("NonFinalFieldInEnum")
    @NonFinal
    Function<String, String> valueFormatter = ((value) -> value);

    @Override
    public String toString() {
        return sql;
    }
}