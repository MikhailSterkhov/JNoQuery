package com.itzstonlex.jnq.sql.request.type;

import com.itzstonlex.jnq.request.query.session.*;
import com.itzstonlex.jnq.request.query.type.RequestFinder;
import com.itzstonlex.jnq.sql.SQLRequest;
import com.itzstonlex.jnq.sql.request.SQLRequestQuery;
import com.itzstonlex.jnq.sql.request.session.*;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SQLRequestFinder extends SQLRequestQuery implements RequestFinder {

    private static final String QUERY = "SELECT {selector} FROM `{content}` {joiner} WHERE {filter} {group} {order}";

    SQLRequestSessionSelector<RequestFinder> sessionSelector = new SQLRequestSessionSelector<>(this);

    SQLRequestSessionSortBy<RequestFinder> sessionOrder = new SQLRequestSessionSortBy<>(this);

    SQLRequestSessionGroupBy<RequestFinder> sessionGroup = new SQLRequestSessionGroupBy<>(this);

    SQLRequestSessionFilter<RequestFinder> sessionFilter = new SQLRequestSessionFilter<>(this);

    SQLRequestSessionJoiner<RequestFinder> sessionJoiner = new SQLRequestSessionJoiner<>(this);

    public SQLRequestFinder(@NonNull SQLRequest request) {
        super(request);
    }

    @Override
    public @NonNull RequestSessionSelector<RequestFinder> sessionSelector() {
        return sessionSelector;
    }

    @Override
    public @NonNull RequestSessionSortBy<RequestFinder> sessionSort() {
        return sessionOrder;
    }

    @Override
    public @NonNull RequestSessionGroupBy<RequestFinder> sessionGroup() {
        return sessionGroup;
    }

    @Override
    public @NonNull RequestSessionFilter<RequestFinder> sessionFilter() {
        return sessionFilter;
    }

    @Override
    public @NonNull RequestSessionJoiner<RequestFinder> sessionJoiner() {
        return sessionJoiner;
    }

    @Override
    protected String toSQL() {
        String query = QUERY.replace("{content}", request.getDataContent().getName());

        query = query.replace("{selector}", sessionSelector.getGeneratedSql());
        query = query.replace("{order}", sessionOrder.getGeneratedSql());
        query = query.replace("{group}", sessionGroup.getGeneratedSql());
        query = query.replace("{filter}", sessionFilter.getGeneratedSql());
        query = query.replace("{joiner}", sessionJoiner.getGeneratedSql());

        return query;
    }

    @Override
    protected Object[] toFieldValues() {
        return new Object[0];
    }
}
