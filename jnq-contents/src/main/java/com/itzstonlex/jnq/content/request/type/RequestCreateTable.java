package com.itzstonlex.jnq.content.request.type;

import com.itzstonlex.jnq.content.field.type.IndexField;
import com.itzstonlex.jnq.content.request.RequestQuery;
import com.itzstonlex.jnq.content.request.session.RequestSessionCollection;
import lombok.NonNull;

public interface RequestCreateTable extends RequestQuery {

    @NonNull
    RequestCreateTable checkAvailability();

    @NonNull
    RequestSessionCollection<IndexField, RequestCreateTable> beginCollection();
}
