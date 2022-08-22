package com.itzstonlex.jnq.jdbc.request.type;

import com.itzstonlex.jnq.request.query.session.RequestSessionFilter;
import com.itzstonlex.jnq.request.query.type.RequestUpdate;
import com.itzstonlex.jnq.jdbc.request.JDBCRequest;
import com.itzstonlex.jnq.jdbc.request.JDBCRequestQuery;
import com.itzstonlex.jnq.jdbc.request.session.JDBCRequestSessionFilter;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JDBCRequestUpdate extends JDBCRequestQuery implements RequestUpdate {

    private static final String QUERY = "UPDATE `{content}` SET {update} WHERE {filter}";

    JDBCRequestSessionFilter<RequestUpdate> sessionUpdater = new JDBCRequestSessionFilter<>(this);

    JDBCRequestSessionFilter<RequestUpdate> sessionFilter = new JDBCRequestSessionFilter<>(this);

    public JDBCRequestUpdate(@NonNull JDBCRequest request) {
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
