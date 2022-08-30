package com.itzstonlex.jnq.jdbc.request;

import com.itzstonlex.jnq.content.field.DataField;
import com.itzstonlex.jnq.content.field.type.EntryField;
import com.itzstonlex.jnq.content.request.RequestQueryBasic;
import com.itzstonlex.jnq.content.request.session.*;
import com.itzstonlex.jnq.jdbc.request.session.*;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PROTECTED)
public class JDBCRequestQueryBasic<Field extends DataField>
        extends JDBCRequestQuery
        implements RequestQueryBasic<Field> {

    String query, sessionFilterReplacement, sessionJoinerReplacement, sessionSelectorReplacement, sessionGroupReplacement, sessionSortReplacement;

    JDBCRequestSessionCollection<Field, RequestQueryBasic<Field>> sessionAppender;

    JDBCRequestSessionCondition<RequestQueryBasic<Field>> sessionFilter;

    JDBCRequestSessionJoiner<RequestQueryBasic<Field>> sessionJoiner;

    JDBCRequestSessionSelector<RequestQueryBasic<Field>> sessionSelector;

    JDBCRequestSessionGrouping<RequestQueryBasic<Field>> sessionGroup;

    JDBCRequestSessionSorting<RequestQueryBasic<Field>> sessionSort;


    public JDBCRequestQueryBasic(@NonNull String query, @NonNull JDBCRequest request) {
        super(request);
        this.query = query;
    }

    @Override
    public @NonNull RequestSessionCollection<Field, RequestQueryBasic<Field>> beginCollection() {
        if (sessionAppender == null) {
            sessionAppender = new JDBCRequestSessionCollection<>(this);
        }
        
        return sessionAppender;
    }

    @Override
    public @NonNull RequestSessionCondition<RequestQueryBasic<Field>> beginCondition(@NonNull String replacement) {
        if (sessionFilter == null) {

            sessionFilter = new JDBCRequestSessionCondition<>(false, this);
            sessionFilterReplacement = replacement;
        }

        return sessionFilter;
    }

    @Override
    public @NonNull RequestSessionJoiner<RequestQueryBasic<Field>> beginJoiner(@NonNull String replacement) {
        if (sessionJoiner == null) {

            sessionJoiner = new JDBCRequestSessionJoiner<>(this);
            sessionJoinerReplacement = replacement;
        }

        return sessionJoiner;
    }

    @Override
    public @NonNull RequestSessionSelector<RequestQueryBasic<Field>> beginSelection(@NonNull String replacement) {
        if (sessionSelector == null) {

            sessionSelector = new JDBCRequestSessionSelector<>(this);
            sessionSelectorReplacement = replacement;
        }

        return sessionSelector;
    }

    @Override
    public @NonNull RequestSessionGrouping<RequestQueryBasic<Field>> beginGrouping(@NonNull String replacement) {
        if (sessionGroup == null) {

            sessionGroup = new JDBCRequestSessionGrouping<>(this);
            sessionGroupReplacement = replacement;
        }

        return sessionGroup;
    }

    @Override
    public @NonNull RequestSessionSorting<RequestQueryBasic<Field>> beginSorting(@NonNull String replacement) {
        if (sessionSort == null) {

            sessionSort = new JDBCRequestSessionSorting<>(this);
            sessionSortReplacement = replacement;
        }

        return sessionSort;
    }

    @Override
    protected String toSQL() {
        query = query.replace("{content}", request.getContent().getName());;

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
                .filter(field -> field instanceof EntryField)
                .map(field -> ((EntryField) field).value())
                .toArray();
    }
}
