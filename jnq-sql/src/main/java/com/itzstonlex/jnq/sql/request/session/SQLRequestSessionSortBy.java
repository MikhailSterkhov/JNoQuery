package com.itzstonlex.jnq.sql.request.session;

import com.itzstonlex.jnq.impl.field.ValueDataField;
import com.itzstonlex.jnq.request.query.RequestQuery;
import com.itzstonlex.jnq.request.query.session.RequestSessionSortBy;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class SQLRequestSessionSortBy<Query extends RequestQuery>
        extends SQLRequestSession<Query>
        implements RequestSessionSortBy<Query> {

    int size;

    @Getter
    String generatedSql = "";

    public SQLRequestSessionSortBy(@NonNull Query parent) {
        super(parent);
    }

    private void _append(ValueDataField field, String suffix) {
        if (generatedSql.isEmpty()) {
            generatedSql += "ORDER BY ";
        }

        generatedSql += (size > 0 ? ", " : "") + "`" + field.name() + "`" + (suffix.isEmpty() ? "" : " " + suffix);
        size++;
    }

    @Override
    public @NonNull RequestSessionSortBy<Query> byDesc(@NonNull ValueDataField field) {
        _append(field, "DESC");
        return this;
    }

    @Override
    public @NonNull RequestSessionSortBy<Query> byAsc(@NonNull ValueDataField field) {
        _append(field, "ASC");
        return this;
    }
}
