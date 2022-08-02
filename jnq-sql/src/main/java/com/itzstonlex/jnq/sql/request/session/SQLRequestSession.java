package com.itzstonlex.jnq.sql.request.session;

import com.itzstonlex.jnq.request.query.RequestQuery;
import com.itzstonlex.jnq.request.query.session.RequestSession;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public abstract class SQLRequestSession<Query extends RequestQuery>
        implements RequestSession<Query> {

    @NonNull
    Query parent;

    @Override
    public @NonNull Query backward() {
        return parent;
    }

}
