package com.itzstonlex.jnq.jdbc.request.type;

import com.itzstonlex.jnq.content.request.session.*;
import com.itzstonlex.jnq.content.request.type.RequestFinder;
import com.itzstonlex.jnq.jdbc.request.JDBCRequest;
import com.itzstonlex.jnq.jdbc.request.JDBCRequestQuery;
import com.itzstonlex.jnq.jdbc.request.session.*;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JDBCRequestFinder extends JDBCRequestQuery implements RequestFinder {

    private static final String QUERY = "SELECT {selection} FROM `{content}` {joiner} {condition} {group} {order} {limit}";

    JDBCRequestSessionSelector<RequestFinder> sessionSelector = new JDBCRequestSessionSelector<>(this);

    JDBCRequestSessionSorting<RequestFinder> sessionOrder = new JDBCRequestSessionSorting<>(this);

    JDBCRequestSessionGrouping<RequestFinder> sessionGroup = new JDBCRequestSessionGrouping<>(this);

    JDBCRequestSessionCondition<RequestFinder> sessionFilter = new JDBCRequestSessionCondition<>(true, this);

    JDBCRequestSessionJoiner<RequestFinder> sessionJoiner = new JDBCRequestSessionJoiner<>(this);

    @NonFinal
    int limitSize;

    public JDBCRequestFinder(@NonNull JDBCRequest request) {
        super(request);
    }

    @Override
    public @NonNull RequestFinder markLimit(int limit) {
        this.limitSize = limit;
        return this;
    }

    @Override
    public @NonNull RequestSessionSelector<RequestFinder> beginSelection() {
        return sessionSelector;
    }

    @Override
    public @NonNull RequestSessionSorting<RequestFinder> beginSorting() {
        return sessionOrder;
    }

    @Override
    public @NonNull RequestSessionGrouping<RequestFinder> beginGrouping() {
        return sessionGroup;
    }

    @Override
    public @NonNull RequestSessionCondition<RequestFinder> beginCondition() {
        return sessionFilter;
    }

    @Override
    public @NonNull RequestSessionJoiner<RequestFinder> beginJoiner() {
        return sessionJoiner;
    }

    @Override
    protected String toSQL() {
        String query = QUERY.replace("{content}", request.getContent().getName());

        query = query.replace("{selection}", sessionSelector.getGeneratedSql());
        query = query.replace("{order}", sessionOrder.getGeneratedSql());
        query = query.replace("{group}", sessionGroup.getGeneratedSql());
        query = query.replace("{condition}", sessionFilter.getGeneratedSql());
        query = query.replace("{joiner}", sessionJoiner.getGeneratedSql());

        query = query.replace("{limit}", limitSize > 0 ? "LIMIT " + limitSize : "");

        return query;
    }

    @Override
    protected Object[] toFieldValues() {
        return sessionFilter.getValues().toArray();
    }
}
