package com.itzstonlex.jnq.request.query.session;

import com.itzstonlex.jnq.impl.field.ValueDataField;
import com.itzstonlex.jnq.request.query.RequestQuery;
import lombok.NonNull;

public interface RequestSessionSortBy<Query extends RequestQuery> extends RequestSession<Query>  {

    @NonNull
    RequestSessionSortBy<Query> byDesc(@NonNull ValueDataField field);

    @NonNull
    RequestSessionSortBy<Query> byAsc(@NonNull ValueDataField field);
}
