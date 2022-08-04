package com.itzstonlex.jnq.sql.request.type;

import com.itzstonlex.jnq.request.query.type.RequestCreateSchema;
import com.itzstonlex.jnq.sql.SQLRequest;
import com.itzstonlex.jnq.sql.request.SQLRequestQuery;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SQLRequestCreateSchema extends SQLRequestQuery implements RequestCreateSchema {

    private static final String QUERY = "CREATE SCHEMA {checker} `{content}`";

    @NonFinal
    boolean existsChecking;

    public SQLRequestCreateSchema(@NonNull SQLRequest request) {
        super(request);
    }

    @Override
    public @NonNull RequestCreateSchema withExistsChecking() {
        this.existsChecking = true;
        return this;
    }

    @Override
    protected String toSQL() {
        String query = QUERY.replace("{content}", request.getDataContent().getName());
        query = query.replace("{checker}", existsChecking ? "IF NOT EXISTS" : "");

        return query;
    }

    @Override
    protected Object[] toFieldValues() {
        return new Object[0];
    }
}
