package com.itzstonlex.jnq.jdbc.request.type;

import com.itzstonlex.jnq.content.field.type.EntryField;
import com.itzstonlex.jnq.content.request.session.RequestSessionCollection;
import com.itzstonlex.jnq.content.request.type.RequestInsert;
import com.itzstonlex.jnq.jdbc.request.JDBCRequest;
import com.itzstonlex.jnq.jdbc.request.JDBCRequestQuery;
import com.itzstonlex.jnq.jdbc.request.session.JDBCRequestSessionCollection;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JDBCRequestInsert extends JDBCRequestQuery implements RequestInsert {

    private static final String QUERY = "INSERT {ignored} INTO `{content}` ({keys}) VALUES ({values})";

    JDBCRequestSessionCollection<EntryField, RequestInsert> duplication = new JDBCRequestSessionCollection<>(this);

    JDBCRequestSessionCollection<EntryField, RequestInsert> appender = new JDBCRequestSessionCollection<>(this);

    @NonFinal
    boolean ignored;

    public JDBCRequestInsert(JDBCRequest request) {
        super(request);
    }

    @Override
    public @NonNull RequestInsert markIgnored() {
        this.ignored = true;
        return this;
    }

    @Override
    public @NonNull RequestSessionCollection<EntryField, RequestInsert> beginDuplication() {
        return duplication;
    }

    @Override
    public @NonNull RequestSessionCollection<EntryField, RequestInsert> beginCollection() {
        return appender;
    }

    @Override
    protected String toSQL() {
        String query = QUERY.replace("{content}", request.getContent().getName());

        List<EntryField> generatedFields = appender.getGeneratedFields();

        query = query.replace("{ignored}", ignored ? "IGNORE" : "");

        query = query.replace("{keys}", generatedFields.stream().map(field -> "`" + field.name() + "`").collect(Collectors.joining(", ")));
        query = query.replace("{values}", generatedFields.stream().map(field -> "?").collect(Collectors.joining(", ")));

        List<EntryField> duplicatedKeys = duplication.getGeneratedFields();

        if (!duplicatedKeys.isEmpty()) {
            query += " ON DUPLICATE KEY UPDATE " + duplication.getGeneratedFields().stream().map(field -> "`" + field.name() + "` = ?").collect(Collectors.joining(", "));
        }

        return query;
    }

    @Override
    protected Object[] toFieldValues() {
        List<Object> values = new ArrayList<>();

        values.addAll(appender.getGeneratedFields().stream().map(EntryField::value).collect(Collectors.toList()));
        values.addAll(duplication.getGeneratedFields().stream().map(EntryField::value).collect(Collectors.toList()));

        return values.toArray();
    }
}
