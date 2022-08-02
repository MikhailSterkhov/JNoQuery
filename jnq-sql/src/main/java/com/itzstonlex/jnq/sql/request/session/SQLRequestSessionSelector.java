package com.itzstonlex.jnq.sql.request.session;

import com.itzstonlex.jnq.request.query.RequestQuery;
import com.itzstonlex.jnq.request.query.session.RequestSessionCast;
import com.itzstonlex.jnq.request.query.session.RequestSessionSelector;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class SQLRequestSessionSelector<Query extends RequestQuery>
        extends SQLRequestSession<Query>
        implements RequestSessionSelector<Query> {

    @Getter
    String generatedSql = "";

    public SQLRequestSessionSelector(@NonNull Query parent) {
        super(parent);
    }

    private void _append(String generatedSql) {
        this.generatedSql += (!this.generatedSql.isEmpty() ? ", " : "") + generatedSql;
    }

    @Override
    public @NonNull Query withAll() {
        this._append("*");
        return backward();
    }

    @Override
    public @NonNull RequestSessionSelector<Query> with(@NonNull String field) {
        return withCasted(field).uncheck();
    }

    @Override
    public @NonNull RequestSessionCast<RequestSessionSelector<Query>, Query> withCasted(@NonNull String field) {
        return new SQLRequestSessionCast<>(this, backward(), (generatedSql) -> _append("`" + field + "`" + generatedSql));
    }

    @Override
    public @NonNull RequestSessionCast<RequestSessionSelector<Query>, Query> withQuery(@NonNull RequestQuery query) {
        return new SQLRequestSessionCast<>(this, backward(), (generatedSql) -> _append("(" + query + ")" + generatedSql));
    }

    @Override
    public @NonNull RequestSessionCast<RequestSessionSelector<Query>, Query> withCount(@NonNull String field) {
        return new SQLRequestSessionCast<>(this, backward(), (generatedSql) -> _append("COUNT(`" + field + "`)" + generatedSql));
    }

    @Override
    public @NonNull RequestSessionCast<RequestSessionSelector<Query>, Query> withLowerCase(@NonNull String field) {
        return new SQLRequestSessionCast<>(this, backward(), (generatedSql) -> _append("LOWER(`" + field + "`)" + generatedSql));
    }

    @Override
    public @NonNull RequestSessionCast<RequestSessionSelector<Query>, Query> withUpperCase(@NonNull String field) {
        return new SQLRequestSessionCast<>(this, backward(), (generatedSql) -> _append("UPPER(`" + field + "`)" + generatedSql));
    }
}
