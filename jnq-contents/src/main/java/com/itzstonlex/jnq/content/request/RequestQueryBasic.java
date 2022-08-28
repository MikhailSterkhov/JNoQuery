package com.itzstonlex.jnq.content.request;

import com.itzstonlex.jnq.content.field.DataField;
import com.itzstonlex.jnq.content.request.session.*;
import lombok.NonNull;

public interface RequestQueryBasic<F extends DataField> extends RequestQuery {

    @NonNull
    RequestSessionCollection<F, RequestQueryBasic<F>> beginCollection();

    @NonNull
    RequestSessionCondition<RequestQueryBasic<F>> beginCondition(@NonNull String replacement);

    @NonNull
    RequestSessionJoiner<RequestQueryBasic<F>> beginJoiner(@NonNull String replacement);

    @NonNull
    RequestSessionSelector<RequestQueryBasic<F>> beginSelection(@NonNull String replacement);

    @NonNull
    RequestSessionGrouping<RequestQueryBasic<F>> beginGrouping(@NonNull String replacement);

    @NonNull
    RequestSessionSorting<RequestQueryBasic<F>> beginSorting(@NonNull String replacement);

    @NonNull
    default RequestSessionCondition<RequestQueryBasic<F>> beginCondition() {
        return beginCondition("{condition}");
    }

    @NonNull
    default RequestSessionJoiner<RequestQueryBasic<F>> beginJoiner() {
        return beginJoiner("{joiner}");
    }

    @NonNull
    default RequestSessionSelector<RequestQueryBasic<F>> beginSelection() {
        return beginSelection("{selection}");
    }

    @NonNull
    default RequestSessionGrouping<RequestQueryBasic<F>> beginGrouping() {
        return beginGrouping("{group}");
    }

    @NonNull
    default RequestSessionSorting<RequestQueryBasic<F>> beginSorting() {
        return beginSorting("{sort}");
    }
}
