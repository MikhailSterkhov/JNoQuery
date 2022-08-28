package com.itzstonlex.jnq.orm.base.request.type;

import com.itzstonlex.jnq.content.field.FieldOperator;
import com.itzstonlex.jnq.content.field.type.EntryField;
import com.itzstonlex.jnq.content.type.SchemaContent;
import com.itzstonlex.jnq.orm.base.request.AbstractMappingRequest;
import com.itzstonlex.jnq.orm.base.request.MappingRequestExecutorImpl;
import com.itzstonlex.jnq.orm.data.ObjectMappingService;
import com.itzstonlex.jnq.orm.request.MappingRequestExecutor;
import com.itzstonlex.jnq.orm.request.type.MappingRequestFinder;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

import java.util.HashMap;
import java.util.Map;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MappingRequestFinderImpl extends AbstractMappingRequest implements MappingRequestFinder {

    Map<EntryField, FieldOperator> and, or;

    @NonFinal
    int limit;

    public MappingRequestFinderImpl(@NonNull SchemaContent schema, @NonNull String table, @NonNull ObjectMappingService objectMappingService) {
        super(schema, table, objectMappingService);

        this.and = new HashMap<>();
        this.or = new HashMap<>();
    }

    @Override
    public int limit() {
        return limit;
    }

    @Override
    public @NonNull Map<EntryField, FieldOperator> entryFieldsAnd() {
        return and;
    }

    @Override
    public @NonNull Map<EntryField, FieldOperator> entryFieldsOr() {
        return or;
    }

    @Override
    public @NonNull MappingRequestFinder limit(int limit) {
        this.limit = limit;
        return this;
    }

    @Override
    public @NonNull MappingRequestFinder and(@NonNull FieldOperator operator, @NonNull EntryField field) {
        and.put(field, operator);
        return this;
    }

    @Override
    public @NonNull MappingRequestFinder or(@NonNull FieldOperator operator, @NonNull EntryField field) {
        or.put(field, operator);
        return this;
    }

    @Override
    public @NonNull MappingRequestExecutor compile() {
        return new MappingRequestExecutorImpl(getSchema(), getTable(), getMapper(), getObjectMappingService(), this);
    }
}
