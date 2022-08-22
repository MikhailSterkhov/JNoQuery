package com.itzstonlex.jnq.jdbc.request.type;

import com.itzstonlex.jnq.impl.field.IndexDataField;
import com.itzstonlex.jnq.request.query.session.RequestSessionAppender;
import com.itzstonlex.jnq.request.query.type.RequestCreateTable;
import com.itzstonlex.jnq.jdbc.request.JDBCRequest;
import com.itzstonlex.jnq.jdbc.request.JDBCRequestQuery;
import com.itzstonlex.jnq.jdbc.request.session.JDBCRequestSessionAppender;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

import java.util.List;
import java.util.stream.Collectors;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JDBCRequestCreateTable extends JDBCRequestQuery implements RequestCreateTable {

    private static final String QUERY = "CREATE TABLE {checker} `{content}` ({values})";

    JDBCRequestSessionAppender<IndexDataField, RequestCreateTable> session = new JDBCRequestSessionAppender<>(this);

    @NonFinal
    boolean existsChecking;

    public JDBCRequestCreateTable(@NonNull JDBCRequest request) {
        super(request);
    }

    @Override
    public @NonNull JDBCRequestCreateTable withExistsChecking() {
        this.existsChecking = true;
        return this;
    }

    @Override
    public @NonNull RequestSessionAppender<IndexDataField, RequestCreateTable> session() {
        return session;
    }

    @Override
    protected String toSQL() {
        String query = QUERY.replace("{content}", request.getDataContent().getName());
        query = query.replace("{checker}", existsChecking ? "IF NOT EXISTS" : "");

        List<IndexDataField> generatedFields = session.getGeneratedFields();

        query = query.replace("{values}", generatedFields.stream().map(IndexDataField::toString).collect(Collectors.joining(", ")));

        return query;
    }

    @Override
    protected Object[] toFieldValues() {
        return new Object[0];
    }
}
