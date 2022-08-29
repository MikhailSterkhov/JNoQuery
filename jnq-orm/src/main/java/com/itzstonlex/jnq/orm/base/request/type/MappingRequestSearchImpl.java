package com.itzstonlex.jnq.orm.base.request.type;

import com.itzstonlex.jnq.content.field.FieldOperator;
import com.itzstonlex.jnq.content.field.type.EntryField;
import com.itzstonlex.jnq.content.type.SchemaContent;
import com.itzstonlex.jnq.orm.base.request.AbstractMappingRequest;
import com.itzstonlex.jnq.orm.base.ObjectMappingRepositoryImpl;
import com.itzstonlex.jnq.orm.data.ObjectMappingService;
import com.itzstonlex.jnq.orm.ObjectMappingRepository;
import com.itzstonlex.jnq.orm.request.type.MappingRequestSearch;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

import java.util.HashMap;
import java.util.Map;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MappingRequestSearchImpl extends AbstractMappingRequest implements MappingRequestSearch {

    Map<EntryField, FieldOperator> and, or;

    @NonFinal
    int limit;

    public MappingRequestSearchImpl(@NonNull SchemaContent schema, @NonNull String table, @NonNull ObjectMappingService objectMappingService) {
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
    public @NonNull MappingRequestSearch markLimit(int limit) {
        this.limit = limit;
        return this;
    }

    @Override
    public @NonNull MappingRequestSearch and(@NonNull FieldOperator operator, @NonNull EntryField field) {
        and.put(field, operator);
        return this;
    }

    @Override
    public @NonNull MappingRequestSearch or(@NonNull FieldOperator operator, @NonNull EntryField field) {
        or.put(field, operator);
        return this;
    }

    @Override
    public @NonNull ObjectMappingRepository compile() {
        return new ObjectMappingRepositoryImpl(getSchema(), getTable(), getMapper(), getObjectMappingService(), this, null);
    }
}
