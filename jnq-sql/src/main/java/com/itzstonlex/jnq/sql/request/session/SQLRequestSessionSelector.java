package com.itzstonlex.jnq.sql.request.session;

import com.itzstonlex.jnq.request.query.RequestQuery;
import com.itzstonlex.jnq.request.query.session.RequestSessionCast;
import com.itzstonlex.jnq.request.query.session.RequestSessionSelector;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class SQLRequestSessionSelector<Query extends RequestQuery>
        extends SQLRequestSession<Query>
        implements RequestSessionSelector<Query> {

    String generatedSql = "";

    public SQLRequestSessionSelector(@NonNull Query parent) {
        super(parent);
    }

    private void _append(String value, String generatedSql) {
        this.generatedSql += (!this.generatedSql.isEmpty() ? ", " : "") + String.format(generatedSql, value);
    }

    public String getGeneratedSql() {
        return generatedSql.isEmpty() ? "*" : generatedSql;
    }

    @Override
    public @NonNull Query withAll() {
        this._append("", "*");
        return backward();
    }

    @Override
    public @NonNull RequestSessionSelector<Query> with(@NonNull String field) {
        return withCasted(field).uncheck();
    }

    @Override
    public @NonNull RequestSessionCast<RequestSessionSelector<Query>, Query> withCasted(@NonNull String field) {
        return new SQLRequestSessionCast<>(this, backward(), (generatedSql) -> _append(field, "`%s`" + generatedSql));
    }

    @Override
    public @NonNull RequestSessionCast<RequestSessionSelector<Query>, Query> withQuery(@NonNull RequestQuery query) {
        return new SQLRequestSessionCast<>(this, backward(), (generatedSql) -> _append(query.toString(), "(%s)" + generatedSql));
    }

    @Override
    public @NonNull RequestSessionCast<RequestSessionSelector<Query>, Query> withCount(@NonNull String field) {
        return new SQLRequestSessionCast<>(this, backward(), (generatedSql) -> _append(field, "COUNT(`%s`)" + generatedSql));
    }

    @Override
    public @NonNull RequestSessionCast<RequestSessionSelector<Query>, Query> withLowerCase(@NonNull String field) {
        return new SQLRequestSessionCast<>(this, backward(), (generatedSql) -> _append(field, "LOWER(`%s`)" + generatedSql));
    }

    @Override
    public @NonNull RequestSessionCast<RequestSessionSelector<Query>, Query> withUpperCase(@NonNull String field) {
        return new SQLRequestSessionCast<>(this, backward(), (generatedSql) -> _append(field, "UPPER(`%s`)" + generatedSql));
    }
}
