package com.itzstonlex.jnq.content.request.type;

import com.itzstonlex.jnq.content.field.type.EntryField;
import com.itzstonlex.jnq.content.request.RequestQuery;
import com.itzstonlex.jnq.content.request.session.RequestSessionCollection;
import lombok.NonNull;

public interface RequestInsert extends RequestQuery {

    @NonNull
    RequestInsert markIgnored();

    @NonNull
    RequestSessionCollection<EntryField, RequestInsert> beginDuplication();

    @NonNull
    RequestSessionCollection<EntryField, RequestInsert> beginCollection();
}
