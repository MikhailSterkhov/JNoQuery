package com.itzstonlex.jnq.jdbc.request.session;

import com.itzstonlex.jnq.content.field.type.EntryField;
import com.itzstonlex.jnq.content.request.RequestQuery;
import com.itzstonlex.jnq.content.request.session.RequestSessionSorting;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class JDBCRequestSessionSorting<Query extends RequestQuery>
        extends JDBCRequestSession<Query>
        implements RequestSessionSorting<Query> {

    int size;

    @Getter
    String generatedSql = "";

    public JDBCRequestSessionSorting(@NonNull Query parent) {
        super(parent);
    }

    private void _append(EntryField field, String suffix) {
        if (generatedSql.isEmpty()) {
            generatedSql += "ORDER BY ";
        }

        generatedSql += (size > 0 ? ", " : "") + "`" + field.name() + "`" + (suffix.isEmpty() ? "" : " " + suffix);
        size++;
    }

    @Override
    public @NonNull RequestSessionSorting<Query> byDesc(@NonNull EntryField field) {
        _append(field, "DESC");
        return this;
    }

    @Override
    public @NonNull RequestSessionSorting<Query> byAsc(@NonNull EntryField field) {
        _append(field, "ASC");
        return this;
    }
}
