package com.itzstonlex.jnq.sql.request.session;

import com.itzstonlex.jnq.request.query.RequestQuery;
import com.itzstonlex.jnq.request.query.session.RequestSessionJoiner;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class SQLRequestSessionJoiner<Query extends RequestQuery>
        extends SQLRequestSession<Query>
        implements RequestSessionJoiner<Query> {

    @Getter
    String generatedSql = "";

    public SQLRequestSessionJoiner(@NonNull Query parent) {
        super(parent);
    }

    @Override
    public @NonNull Query joinAt(@NonNull String table, @NonNull Direction direction, @NonNull Type type, @NonNull String from, @NonNull String to) {
        String directionString = (direction == Direction.EMPTY ? "" : direction.name());
        String typeString = (type == Type.EMPTY ? "" : type.name());

        generatedSql = (directionString + " " + typeString + " JOIN `" + table + "` ON `" + from + "` = `" + to + "`");

        return super.backward();
    }

}
