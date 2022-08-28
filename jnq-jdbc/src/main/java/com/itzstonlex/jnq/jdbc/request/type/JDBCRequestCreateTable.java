package com.itzstonlex.jnq.jdbc.request.type;

import com.itzstonlex.jnq.content.field.type.IndexField;
import com.itzstonlex.jnq.content.request.session.RequestSessionCollection;
import com.itzstonlex.jnq.content.request.type.RequestCreateTable;
import com.itzstonlex.jnq.jdbc.request.JDBCRequest;
import com.itzstonlex.jnq.jdbc.request.JDBCRequestQuery;
import com.itzstonlex.jnq.jdbc.request.session.JDBCRequestSessionCollection;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

import java.util.List;
import java.util.stream.Collectors;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JDBCRequestCreateTable extends JDBCRequestQuery implements RequestCreateTable {

    private static final String QUERY = "CREATE TABLE {checker} `{content}` ({values})";

    JDBCRequestSessionCollection<IndexField, RequestCreateTable> session = new JDBCRequestSessionCollection<>(this);

    @NonFinal
    boolean existsChecking;

    public JDBCRequestCreateTable(@NonNull JDBCRequest request) {
        super(request);
    }

    @Override
    public @NonNull JDBCRequestCreateTable checkAvailability() {
        this.existsChecking = true;
        return this;
    }

    @Override
    public @NonNull RequestSessionCollection<IndexField, RequestCreateTable> beginCollection() {
        return session;
    }

    @Override
    protected String toSQL() {
        String query = QUERY.replace("{content}", request.getContent().getName());
        query = query.replace("{checker}", existsChecking ? "IF NOT EXISTS" : "");

        List<IndexField> generatedFields = session.getGeneratedFields();

        query = query.replace("{values}", generatedFields.stream().map(IndexField::toString).collect(Collectors.joining(", ")));

        return query;
    }

    @Override
    protected Object[] toFieldValues() {
        return new Object[0];
    }
}
