package com.itzstonlex.jnq.jdbc.request.session;

import com.itzstonlex.jnq.content.field.type.EntryField;
import com.itzstonlex.jnq.content.request.RequestQuery;
import com.itzstonlex.jnq.content.request.session.RequestSessionGrouping;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class JDBCRequestSessionGrouping<Query extends RequestQuery>
        extends JDBCRequestSession<Query>
        implements RequestSessionGrouping<Query> {

    int size;

    @Getter
    String generatedSql = "";

    public JDBCRequestSessionGrouping(@NonNull Query parent) {
        super(parent);
    }

    @Override
    public @NonNull RequestSessionGrouping<Query> by(@NonNull EntryField field) {
        if (generatedSql.isEmpty()) {
            generatedSql += "GROUP BY ";
        }

        generatedSql += (size > 0 ? ", " : "") + "`" + field.name() + "`";
        size++;

        return this;
    }

}
