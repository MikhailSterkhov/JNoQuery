package com.itzstonlex.jnq.content.request.session;

import com.itzstonlex.jnq.content.request.RequestQuery;
import lombok.NonNull;

public interface RequestSessionJoiner<Query extends RequestQuery> extends RequestSession<Query>  {

    enum Direction {

        EMPTY, RIGHT, LEFT, FULL
    }

    enum Type {

        EMPTY, INNER, OUTER
    }

    @NonNull
    Query joinAt(@NonNull String table, @NonNull Direction direction, @NonNull Type type, @NonNull String from, @NonNull String to);

    @NonNull
    default Query joinAt(@NonNull String table, @NonNull Type type, @NonNull String from, @NonNull String to) {
        return joinAt(table, Direction.EMPTY, type, from, to);
    }

    @NonNull
    default Query joinAt(@NonNull String table, @NonNull Direction direction, @NonNull String from, @NonNull String to) {
        return joinAt(table, direction, Type.EMPTY, from, to);
    }

    @NonNull
    default Query joinAt(@NonNull String table, @NonNull String from, @NonNull String to) {
        return joinAt(table, Direction.EMPTY, from, to);
    }

}
