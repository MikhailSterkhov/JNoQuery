package com.itzstonlex.jnq.sql.request.session;

import com.itzstonlex.jnq.field.FieldOperator;
import com.itzstonlex.jnq.impl.field.ValueDataField;
import com.itzstonlex.jnq.request.query.RequestQuery;
import com.itzstonlex.jnq.request.query.session.RequestSessionFilter;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class SQLRequestSessionFilter<Query extends RequestQuery>
        extends SQLRequestSession<Query>
        implements RequestSessionFilter<Query> {

    @Getter
    String generatedSql = "";

    public SQLRequestSessionFilter(@NonNull Query parent) {
        super(parent);
    }

    private void _appendField(StringBuilder stringBuilder, FieldOperator operator, ValueDataField field) {
        Object value = field.value();

        if (value != null) {
            stringBuilder.append(field.toString().replaceFirst("\\=", operator.getSql()));
        }
    }

    @Override
    public @NonNull RequestSessionFilter<Query> and(@NonNull FieldOperator operator, @NonNull ValueDataField field) {
        StringBuilder builder = new StringBuilder(generatedSql);

        if (!generatedSql.isEmpty()) {
            builder.append(" AND ");
        }

        _appendField(builder, operator, field);
        generatedSql = builder.toString();

        return this;
    }

    @Override
    public @NonNull RequestSessionFilter<Query> or(@NonNull FieldOperator operator, @NonNull ValueDataField field) {
        StringBuilder builder = new StringBuilder(generatedSql);

        if (!generatedSql.isEmpty()) {
            builder.append(" OR ");
        }

        _appendField(builder, operator, field);
        generatedSql = builder.toString();

        return this;
    }
}
