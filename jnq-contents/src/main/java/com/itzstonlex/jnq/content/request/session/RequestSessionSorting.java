package com.itzstonlex.jnq.content.request.session;

import com.itzstonlex.jnq.content.field.type.EntryField;
import com.itzstonlex.jnq.content.request.RequestQuery;
import lombok.NonNull;

public interface RequestSessionSorting<Query extends RequestQuery> extends RequestSession<Query>  {

    @NonNull
    RequestSessionSorting<Query> byDesc(@NonNull EntryField field);

    @NonNull
    RequestSessionSorting<Query> byAsc(@NonNull EntryField field);
}
