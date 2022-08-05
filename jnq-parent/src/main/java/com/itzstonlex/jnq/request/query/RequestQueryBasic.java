package com.itzstonlex.jnq.request.query;

import com.itzstonlex.jnq.field.DataField;
import com.itzstonlex.jnq.request.query.session.*;
import lombok.NonNull;

public interface RequestQueryBasic<F extends DataField> extends RequestQuery {

    @NonNull
    RequestSessionAppender<F, RequestQueryBasic<F>> sessionAppender();

    @NonNull
    RequestSessionFilter<RequestQueryBasic<F>> sessionFilter(@NonNull String replacement);

    @NonNull
    RequestSessionJoiner<RequestQueryBasic<F>> sessionJoiner(@NonNull String replacement);

    @NonNull
    RequestSessionSelector<RequestQueryBasic<F>> sessionSelector(@NonNull String replacement);

    @NonNull
    RequestSessionGroupBy<RequestQueryBasic<F>> sessionGroup(@NonNull String replacement);

    @NonNull
    RequestSessionSortBy<RequestQueryBasic<F>> sessionSort(@NonNull String replacement);

    @NonNull
    default RequestSessionFilter<RequestQueryBasic<F>> sessionFilter() {
        return sessionFilter("{filter}");
    }

    @NonNull
    default RequestSessionJoiner<RequestQueryBasic<F>> sessionJoiner() {
        return sessionJoiner("{joiner}");
    }

    @NonNull
    default RequestSessionSelector<RequestQueryBasic<F>> sessionSelector() {
        return sessionSelector("{selector}");
    }

    @NonNull
    default RequestSessionGroupBy<RequestQueryBasic<F>> sessionGroup() {
        return sessionGroup("{group}");
    }

    @NonNull
    default RequestSessionSortBy<RequestQueryBasic<F>> sessionSort() {
        return sessionSort("{sort}");
    }
}
