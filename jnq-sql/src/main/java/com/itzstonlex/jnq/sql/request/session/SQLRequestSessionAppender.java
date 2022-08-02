package com.itzstonlex.jnq.sql.request.session;

import com.itzstonlex.jnq.field.DataField;
import com.itzstonlex.jnq.request.query.RequestQuery;
import com.itzstonlex.jnq.request.query.session.RequestSessionAppender;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SQLRequestSessionAppender<Field extends DataField, Query extends RequestQuery>
        extends SQLRequestSession<Query>
        implements RequestSessionAppender<Field, Query> {

    @Getter
    List<Field> generatedFields = new ArrayList<>();

    public SQLRequestSessionAppender(@NonNull Query parent) {
        super(parent);
    }

    @Override
    public @NonNull RequestSessionAppender<Field, Query> append(@NonNull Field field) {
        generatedFields.add(field);
        return this;
    }
}
