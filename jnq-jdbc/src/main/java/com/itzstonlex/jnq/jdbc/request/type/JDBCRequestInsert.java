package com.itzstonlex.jnq.jdbc.request.type;

import com.itzstonlex.jnq.impl.field.ValueDataField;
import com.itzstonlex.jnq.request.query.session.RequestSessionAppender;
import com.itzstonlex.jnq.request.query.type.RequestInsert;
import com.itzstonlex.jnq.jdbc.JDBCRequest;
import com.itzstonlex.jnq.jdbc.request.JDBCRequestQuery;
import com.itzstonlex.jnq.jdbc.request.session.JDBCRequestSessionAppender;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

import java.util.List;
import java.util.stream.Collectors;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JDBCRequestInsert extends JDBCRequestQuery implements RequestInsert {

    private static final String QUERY = "INSERT {ignored} INTO `{content}` ({keys}) VALUES ({values}) {update_keys}";

    JDBCRequestSessionAppender<ValueDataField, RequestInsert> appender = new JDBCRequestSessionAppender<>(this);

    @NonFinal
    boolean ignored, updateOnDuplicateKeys;

    public JDBCRequestInsert(JDBCRequest request) {
        super(request);
    }

    @Override
    public @NonNull RequestInsert withIgnored() {
        this.ignored = true;
        return this;
    }

    @Override
    public @NonNull RequestInsert withUpdateDuplicatedKeys() {
        this.updateOnDuplicateKeys = true;
        return this;
    }

    @Override
    public @NonNull RequestSessionAppender<ValueDataField, RequestInsert> session() {
        return appender;
    }

    @Override
    protected String toSQL() {
        String query = QUERY.replace("{content}", request.getDataContent().getName());

        List<ValueDataField> generatedFields = appender.getGeneratedFields();

        query = query.replace("{ignored}", ignored ? "IGNORE" : "");

        query = query.replace("{keys}", generatedFields.stream().map(field -> "`" + field.name() + "`").collect(Collectors.joining(", ")));
        query = query.replace("{values}", generatedFields.stream().map(field -> field.value() instanceof Number ? field.value().toString() : "'" + field.value() + "'").collect(Collectors.joining(", ")));

        String suffix = generatedFields.stream().map(ValueDataField::toString).collect(Collectors.joining(", "));
        query = query.replace("{update_keys}", !updateOnDuplicateKeys ? "" : "ON DUPLICATE KEY UPDATE " + suffix);

        return query;
    }

    @Override
    protected Object[] toFieldValues() {
        return null;
    }
}
