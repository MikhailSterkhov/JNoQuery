package com.itzstonlex.jnq.request.query.session;

import com.itzstonlex.jnq.impl.field.ValueDataField;
import com.itzstonlex.jnq.request.query.RequestQuery;
import lombok.NonNull;

public interface RequestSessionGroupBy<Query extends RequestQuery> extends RequestSession<Query>  {

    @NonNull
    RequestSessionGroupBy<Query> by(@NonNull ValueDataField field);
}
