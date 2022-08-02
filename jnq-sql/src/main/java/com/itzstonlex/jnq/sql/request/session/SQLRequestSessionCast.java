package com.itzstonlex.jnq.sql.request.session;

import com.itzstonlex.jnq.request.query.RequestQuery;
import com.itzstonlex.jnq.request.query.session.RequestSessionCast;
import com.itzstonlex.jnq.request.query.session.RequestSessionSelector;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

import java.util.function.Consumer;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SQLRequestSessionCast<Session extends RequestSessionSelector<Query>, Query extends RequestQuery>
        extends SQLRequestSession<Query>
        implements RequestSessionCast<Session, Query> {

    Session session;
    Consumer<String> castGenerateConsumer;

    public SQLRequestSessionCast(@NonNull Session session, @NonNull Query parent, @NonNull Consumer<String> castGenerateConsumer) {
        super(parent);

        this.session = session;
        this.castGenerateConsumer = castGenerateConsumer;
    }

    @Override
    public @NonNull Session as(@NonNull String cast) {
        castGenerateConsumer.accept(" AS `" + cast + "`");
        return session;
    }

    @Override
    public @NonNull Session uncheck() {
        return session;
    }
}
