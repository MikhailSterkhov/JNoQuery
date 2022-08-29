package com.itzstonlex.jnq.content.request.session;

import com.itzstonlex.jnq.content.field.DataField;
import com.itzstonlex.jnq.content.request.RequestQuery;
import lombok.NonNull;

import java.util.Collection;

public interface RequestSessionCollection<Field extends DataField, Query extends RequestQuery> extends RequestSession<Query> {

    @NonNull
    RequestSessionCollection<Field, Query> add(@NonNull Field field);

    @NonNull
    RequestSessionCollection<Field, Query> addAll(@NonNull Iterable<Field> fields);
}
