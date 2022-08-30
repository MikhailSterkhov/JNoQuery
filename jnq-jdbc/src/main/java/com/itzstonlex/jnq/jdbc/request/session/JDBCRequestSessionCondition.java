package com.itzstonlex.jnq.jdbc.request.session;

import com.itzstonlex.jnq.content.field.FieldOperator;
import com.itzstonlex.jnq.content.field.type.EntryField;
import com.itzstonlex.jnq.content.request.RequestQuery;
import com.itzstonlex.jnq.content.request.session.RequestSessionCondition;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

import java.util.LinkedHashSet;
import java.util.Set;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JDBCRequestSessionCondition<Query extends RequestQuery>
        extends JDBCRequestSession<Query>
        implements RequestSessionCondition<Query> {

    boolean anonymous;

    @NonFinal
    @Getter
    String generatedSql = "";

    @Getter
    Set<Object> values = new LinkedHashSet<>();

    public JDBCRequestSessionCondition(boolean anonymous, @NonNull Query parent) {
        super(parent);
        this.anonymous = anonymous;
    }

    private void _appendField(StringBuilder stringBuilder, FieldOperator operator, EntryField field) {
        Object value = field.value();

        if (value != null) {
            if (anonymous) {
                stringBuilder.append("`").append(field.name()).append("`").append(operator).append("?");
            }
            else {
                stringBuilder.append(field.toString().replaceFirst("\\=", operator.getSql()));
            }
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
        values.add(field.value());
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
