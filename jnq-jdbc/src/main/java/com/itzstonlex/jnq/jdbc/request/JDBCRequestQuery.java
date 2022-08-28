package com.itzstonlex.jnq.jdbc.request;

import com.itzstonlex.jnq.content.RequestExecutor;
import com.itzstonlex.jnq.content.request.RequestQuery;
import com.itzstonlex.jnq.jdbc.JDBCStatement;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PROTECTED, makeFinal = true)
public abstract class JDBCRequestQuery implements RequestQuery {

    JDBCRequest request;

    protected abstract String toSQL();

    protected abstract Object[] toFieldValues();

    @Override
    public @NonNull RequestExecutor compile() {
        JDBCStatement statement = new JDBCStatement(request, toFieldValues());
        return new JDBCRequestExecutor(toSQL(), statement);
    }

    @Override
    public String toString() {
        return toSQL();
    }
}
