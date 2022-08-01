package com.itzstonlex.jnq.request.query.type;

import com.itzstonlex.jnq.impl.field.IndexDataField;
import com.itzstonlex.jnq.request.query.RequestQuery;
import com.itzstonlex.jnq.request.query.session.RequestSessionAppender;
import lombok.NonNull;

public interface RequestAlterAdd extends RequestQuery {

    @NonNull
    RequestSessionAppender<IndexDataField, RequestAlterAdd> session();
}
