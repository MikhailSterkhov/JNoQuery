package com.itzstonlex.jnq.field.request;

import com.itzstonlex.jnq.field.DataField;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

import java.util.function.Function;

public interface FieldWhereRequest<R, F extends DataField> {

    @Getter
    @RequiredArgsConstructor
    @AllArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
    enum Operator {

        LIKE("LIKE", ((value) -> "%" + value + "%")),

        EQUAL("="),

        MORE(">"),

        LESS("<"),
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

    @NonNull
    R where(@NonNull Operator operator, @NonNull F field);

    default @NonNull R where(@NonNull F field) {
        return where(Operator.EQUAL, field);
    }
}
