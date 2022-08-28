package com.itzstonlex.jnq.jdbc.request.session;

import com.itzstonlex.jnq.content.request.RequestQuery;
import com.itzstonlex.jnq.content.request.session.RequestSession;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public abstract class JDBCRequestSession<Query extends RequestQuery>
        implements RequestSession<Query> {

    @NonNull
    Query parent;

    @Override
    public @NonNull Query endpoint() {
        return parent;
    }

}
