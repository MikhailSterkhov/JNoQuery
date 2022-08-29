package com.itzstonlex.jnq.jdbc.request.session;

import com.itzstonlex.jnq.content.field.DataField;
import com.itzstonlex.jnq.content.request.RequestQuery;
import com.itzstonlex.jnq.content.request.session.RequestSessionCollection;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JDBCRequestSessionCollection<Field extends DataField, Query extends RequestQuery>
        extends JDBCRequestSession<Query>
        implements RequestSessionCollection<Field, Query> {

    @Getter
    List<Field> generatedFields = new ArrayList<>();

    public JDBCRequestSessionCollection(@NonNull Query parent) {
        super(parent);
    }

    @Override
    public @NonNull RequestSessionCollection<Field, Query> add(@NonNull Field field) {
        generatedFields.add(field);
        return this;
    }

    @Override
    public @NonNull RequestSessionCollection<Field, Query> addAll(@NonNull Iterable<Field> fields) {
        fields.forEach(generatedFields::add);
        return this;
    }
}
