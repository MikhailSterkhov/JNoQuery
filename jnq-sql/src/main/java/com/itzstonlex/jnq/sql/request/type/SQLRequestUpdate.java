package com.itzstonlex.jnq.sql.request.type;

import com.itzstonlex.jnq.request.query.session.RequestSessionFilter;
import com.itzstonlex.jnq.request.query.type.RequestUpdate;
import com.itzstonlex.jnq.sql.SQLRequest;
import com.itzstonlex.jnq.sql.request.SQLRequestQuery;
import com.itzstonlex.jnq.sql.request.session.SQLRequestSessionFilter;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SQLRequestUpdate extends SQLRequestQuery implements RequestUpdate {

    private static final String QUERY = "UPDATE `{content}` SET {update} WHERE {filter}";

    SQLRequestSessionFilter<RequestUpdate> sessionUpdater = new SQLRequestSessionFilter<>(this);

    SQLRequestSessionFilter<RequestUpdate> sessionFilter = new SQLRequestSessionFilter<>(this);

    public SQLRequestUpdate(@NonNull SQLRequest request) {
        super(request);
    }

    @Override
    public @NonNull RequestSessionFilter<RequestUpdate> sessionUpdater() {
        return sessionUpdater;
    }

    @Override
    public @NonNull RequestSessionFilter<RequestUpdate> sessionFilter() {
        return sessionFilter;
    }

    @Override
    protected String toSQL() {
        String query = QUERY.replace("{content}", request.getDataContent().getName());

        query = query.replace("{update}", sessionUpdater.getGeneratedSql().replace("WHERE ", ""));
        query = query.replace("{filter}", sessionFilter.getGeneratedSql().replace("WHERE ", ""));

        return query;
    }

    @Override
    protected Object[] toFieldValues() {
        return null;
    }
}
