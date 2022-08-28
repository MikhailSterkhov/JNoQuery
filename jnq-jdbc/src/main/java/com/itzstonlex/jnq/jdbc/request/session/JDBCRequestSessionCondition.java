package com.itzstonlex.jnq.jdbc.request.session;

import com.itzstonlex.jnq.content.field.FieldOperator;
import com.itzstonlex.jnq.content.field.type.EntryField;
import com.itzstonlex.jnq.content.request.RequestQuery;
import com.itzstonlex.jnq.content.request.session.RequestSessionCondition;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class JDBCRequestSessionCondition<Query extends RequestQuery>
        extends JDBCRequestSession<Query>
        implements RequestSessionCondition<Query> {

    @Getter
    String generatedSql = "";

    public JDBCRequestSessionCondition(@NonNull Query parent) {
        super(parent);
    }

    private void _appendField(StringBuilder stringBuilder, FieldOperator operator, EntryField field) {
        Object value = field.value();

        if (value != null) {
            stringBuilder.append(field.toString().replaceFirst("\\=", operator.getSql()));
        }
    }

    private void _append(String delimiter, FieldOperator operator, EntryField field) {
        StringBuilder builder = new StringBuilder(generatedSql);

        if (!generatedSql.isEmpty()) {
            builder.append(" ").append(delimiter).append(" ");
        }
        else {
            builder.append("WHERE ");
        }

        _appendField(builder, operator, field);
        generatedSql = builder.toString();
    }

    @Override
    public @NonNull RequestSessionCondition<Query> and(@NonNull FieldOperator operator, @NonNull EntryField field) {
        this._append("AND", operator, field);
        return this;
    }

    @Override
    public @NonNull RequestSessionCondition<Query> or(@NonNull FieldOperator operator, @NonNull EntryField field) {
        this._append("OR", operator, field);
        return this;
    }
}
