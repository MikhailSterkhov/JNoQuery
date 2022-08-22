package com.itzstonlex.jnq.jdbc.request;

import com.itzstonlex.jnq.field.DataField;
import com.itzstonlex.jnq.impl.field.ValueDataField;
import com.itzstonlex.jnq.request.query.RequestQueryBasic;
import com.itzstonlex.jnq.request.query.session.*;
import com.itzstonlex.jnq.jdbc.JDBCRequest;
import com.itzstonlex.jnq.jdbc.request.session.*;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PROTECTED)
public class JDBCRequestQueryBasic<Field extends DataField>
        extends JDBCRequestQuery
        implements RequestQueryBasic<Field> {

    String query, sessionFilterReplacement, sessionJoinerReplacement, sessionSelectorReplacement, sessionGroupReplacement, sessionSortReplacement;

    JDBCRequestSessionAppender<Field, RequestQueryBasic<Field>> sessionAppender;

    JDBCRequestSessionFilter<RequestQueryBasic<Field>> sessionFilter;

    JDBCRequestSessionJoiner<RequestQueryBasic<Field>> sessionJoiner;

    JDBCRequestSessionSelector<RequestQueryBasic<Field>> sessionSelector;

    JDBCRequestSessionGroupBy<RequestQueryBasic<Field>> sessionGroup;

    JDBCRequestSessionSortBy<RequestQueryBasic<Field>> sessionSort;


    public JDBCRequestQueryBasic(@NonNull String query, @NonNull JDBCRequest request) {
        super(request);
        this.query = query;
    }

    @Override
    public @NonNull RequestSessionAppender<Field, RequestQueryBasic<Field>> sessionAppender() {
        if (sessionAppender == null) {
            sessionAppender = new JDBCRequestSessionAppender<>(this);
        }
        
        return sessionAppender;
    }

    @Override
    public @NonNull RequestSessionFilter<RequestQueryBasic<Field>> sessionFilter(@NonNull String replacement) {
        if (sessionFilter == null) {

            sessionFilter = new JDBCRequestSessionFilter<>(this);
            sessionFilterReplacement = replacement;
        }

        return sessionFilter;
    }

    @Override
    public @NonNull RequestSessionJoiner<RequestQueryBasic<Field>> sessionJoiner(@NonNull String replacement) {
        if (sessionJoiner == null) {

            sessionJoiner = new JDBCRequestSessionJoiner<>(this);
            sessionJoinerReplacement = replacement;
        }

        return sessionJoiner;
    }

    @Override
    public @NonNull RequestSessionSelector<RequestQueryBasic<Field>> sessionSelector(@NonNull String replacement) {
        if (sessionSelector == null) {

            sessionSelector = new JDBCRequestSessionSelector<>(this);
            sessionSelectorReplacement = replacement;
        }

        return sessionSelector;
    }

    @Override
    public @NonNull RequestSessionGroupBy<RequestQueryBasic<Field>> sessionGroup(@NonNull String replacement) {
        if (sessionGroup == null) {

            sessionGroup = new JDBCRequestSessionGroupBy<>(this);
            sessionGroupReplacement = replacement;
        }

        return sessionGroup;
    }

    @Override
    public @NonNull RequestSessionSortBy<RequestQueryBasic<Field>> sessionSort(@NonNull String replacement) {
        if (sessionSort == null) {

            sessionSort = new JDBCRequestSessionSortBy<>(this);
            sessionSortReplacement = replacement;
        }

        return sessionSort;
    }

    @Override
    protected String toSQL() {
        query = query.replace("{content}", request.getDataContent().getName());;

        if (sessionFilterReplacement != null) {
            query = query.replace(sessionFilterReplacement, sessionFilter == null ? "" : sessionFilter.getGeneratedSql());
        }

        if (sessionJoinerReplacement != null) {
            query = query.replace(sessionJoinerReplacement, sessionJoiner == null ? "" : sessionJoiner.getGeneratedSql());
        }

        if (sessionSelectorReplacement != null) {
            query = query.replace(sessionSelectorReplacement, sessionSelector == null ? "" : sessionSelector.getGeneratedSql());
        }

        if (sessionGroupReplacement != null) {
            query = query.replace(sessionGroupReplacement, sessionGroup == null ? "" : sessionGroup.getGeneratedSql());
        }

        if (sessionSortReplacement != null) {
            query = query.replace(sessionSortReplacement, sessionSort == null ? "" : sessionSort.getGeneratedSql());
        }

        if (sessionAppender != null) {

            int index = 0;

            for (Field field : sessionAppender.getGeneratedFields()) {

                query = query.replace("{" + index + "}", field.toString());
                query = query.replace("{name" + index + "}", field.name());

                index++;
            }
        }

        return query;
    }

    @Override
    protected Object[] toFieldValues() {
        if (!toSQL().contains("?")) {
            return null;
        }

        return sessionAppender == null ? null : sessionAppender.getGeneratedFields()
                .stream()
                .filter(field -> field instanceof ValueDataField)
                .map(field -> ((ValueDataField) field).value())
                .toArray();
    }
}
