package com.itzstonlex.jnq.jdbc.request.session;

import com.itzstonlex.jnq.impl.field.ValueDataField;
import com.itzstonlex.jnq.request.query.RequestQuery;
import com.itzstonlex.jnq.request.query.session.RequestSessionGroupBy;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class JDBCRequestSessionGroupBy<Query extends RequestQuery>
        extends JDBCRequestSession<Query>
        implements RequestSessionGroupBy<Query> {

    int size;

    @Getter
    String generatedSql = "";

    public JDBCRequestSessionGroupBy(@NonNull Query parent) {
        super(parent);
    }

    @Override
    public @NonNull RequestSessionGroupBy<Query> by(@NonNull ValueDataField field) {
        if (generatedSql.isEmpty()) {
            generatedSql += "GROUP BY ";
        }

        generatedSql += (size > 0 ? ", " : "") + "`" + field.name() + "`";
        size++;

        return this;
    }

}
