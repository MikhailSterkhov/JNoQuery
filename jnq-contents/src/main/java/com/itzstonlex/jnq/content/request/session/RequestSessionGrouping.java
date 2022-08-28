package com.itzstonlex.jnq.content.request.session;

import com.itzstonlex.jnq.content.field.type.EntryField;
import com.itzstonlex.jnq.content.request.RequestQuery;
import lombok.NonNull;

public interface RequestSessionGrouping<Query extends RequestQuery> extends RequestSession<Query>  {

    @NonNull
    RequestSessionGrouping<Query> by(@NonNull EntryField field);
}
