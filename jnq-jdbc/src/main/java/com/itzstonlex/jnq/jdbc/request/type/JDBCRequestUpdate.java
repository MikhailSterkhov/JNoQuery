package com.itzstonlex.jnq.jdbc.request.type;

import com.itzstonlex.jnq.content.request.session.RequestSessionCondition;
import com.itzstonlex.jnq.content.request.type.RequestUpdate;
import com.itzstonlex.jnq.jdbc.request.JDBCRequest;
import com.itzstonlex.jnq.jdbc.request.JDBCRequestQuery;
import com.itzstonlex.jnq.jdbc.request.session.JDBCRequestSessionCondition;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JDBCRequestUpdate extends JDBCRequestQuery implements RequestUpdate {

    private static final String QUERY = "UPDATE `{content}` SET {update} WHERE {condition}";

    JDBCRequestSessionCondition<RequestUpdate> sessionUpdater = new JDBCRequestSessionCondition<>(this);

    JDBCRequestSessionCondition<RequestUpdate> sessionFilter = new JDBCRequestSessionCondition<>(this);

    public JDBCRequestUpdate(@NonNull JDBCRequest request) {
        super(request);
    }

    @Override
    public @NonNull RequestSessionCondition<RequestUpdate> beginUpdateCondition() {
        return sessionUpdater;
    }

    @Override
    public @NonNull RequestSessionCondition<RequestUpdate> beginCondition() {
        return sessionFilter;
    }

    @Override
    protected String toSQL() {
        String query = QUERY.replace("{content}", request.getContent().getName());

        query = query.replace("{update}", sessionUpdater.getGeneratedSql().replace("WHERE ", ""));
        query = query.replace("{condition}", sessionFilter.getGeneratedSql().replace("WHERE ", ""));

        return query;
    }

    @Override
    protected Object[] toFieldValues() {
        return null;
    }
}
