package com.itzstonlex.jnq.request.query.type;

import com.itzstonlex.jnq.impl.field.IndexDataField;
import com.itzstonlex.jnq.request.query.RequestQuery;
import com.itzstonlex.jnq.request.query.session.RequestSessionAppender;
import lombok.NonNull;

public interface RequestCreateTable extends RequestQuery {

    @NonNull
    RequestCreateTable withExistsChecking();

    @NonNull
    RequestSessionAppender<IndexDataField, RequestCreateTable> session();
}
