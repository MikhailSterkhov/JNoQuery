package com.itzstonlex.jnq.jdbc.request.type;

import com.itzstonlex.jnq.request.query.session.*;
import com.itzstonlex.jnq.request.query.type.RequestFinder;
import com.itzstonlex.jnq.jdbc.JDBCRequest;
import com.itzstonlex.jnq.jdbc.request.JDBCRequestQuery;
import com.itzstonlex.jnq.jdbc.request.session.*;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JDBCRequestFinder extends JDBCRequestQuery implements RequestFinder {

    private static final String QUERY = "SELECT {selector} FROM `{content}` {joiner} {filter} {group} {order} {limit}";

    JDBCRequestSessionSelector<RequestFinder> sessionSelector = new JDBCRequestSessionSelector<>(this);

    JDBCRequestSessionSortBy<RequestFinder> sessionOrder = new JDBCRequestSessionSortBy<>(this);

    JDBCRequestSessionGroupBy<RequestFinder> sessionGroup = new JDBCRequestSessionGroupBy<>(this);

    JDBCRequestSessionFilter<RequestFinder> sessionFilter = new JDBCRequestSessionFilter<>(this);

    JDBCRequestSessionJoiner<RequestFinder> sessionJoiner = new JDBCRequestSessionJoiner<>(this);

    @NonFinal
    int limitSize;

    public JDBCRequestFinder(@NonNull JDBCRequest request) {
        super(request);
    }

    @Override
    public @NonNull RequestFinder withLimit(int limit) {
        this.limitSize = limit;
        return this;
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

        query = query.replace("{limit}", limitSize > 0 ? "LIMIT " + limitSize : "");

        return query;
    }

    @Override
    protected Object[] toFieldValues() {
        return new Object[0];
    }
}
