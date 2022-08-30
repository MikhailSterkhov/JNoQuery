package com.itzstonlex.jnq.jdbc.request.type;

import com.itzstonlex.jnq.content.field.type.EntryField;
import com.itzstonlex.jnq.content.request.session.RequestSessionCondition;
import com.itzstonlex.jnq.content.request.type.RequestUpdate;
import com.itzstonlex.jnq.jdbc.request.JDBCRequest;
import com.itzstonlex.jnq.jdbc.request.JDBCRequestQuery;
import com.itzstonlex.jnq.jdbc.request.session.JDBCRequestSessionCondition;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JDBCRequestUpdate extends JDBCRequestQuery implements RequestUpdate {

    private static final String QUERY = "UPDATE `{content}` SET {update} WHERE {condition}";

    JDBCRequestSessionCondition<RequestUpdate> sessionUpdater = new JDBCRequestSessionCondition<>(true, this);

    JDBCRequestSessionCondition<RequestUpdate> sessionFilter = new JDBCRequestSessionCondition<>(true, this);

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
        Set<Object> values = new LinkedHashSet<>();

        values.addAll(sessionUpdater.getValues());
        values.addAll(sessionFilter.getValues());

        return values.toArray();
    }
}
