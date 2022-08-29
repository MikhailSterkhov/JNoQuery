package com.itzstonlex.jnq.orm.request.type;

import com.itzstonlex.jnq.content.field.FieldOperator;
import com.itzstonlex.jnq.content.field.type.EntryField;
import com.itzstonlex.jnq.orm.request.MappingRequest;
import lombok.NonNull;

import java.util.Map;

public interface MappingRequestSearch extends MappingRequest {

    int limit();

    @NonNull
    Map<EntryField, FieldOperator> entryFieldsAnd();

    @NonNull
    Map<EntryField, FieldOperator> entryFieldsOr();

    @NonNull
    MappingRequestSearch markLimit(int limit);

    @NonNull
    MappingRequestSearch and(@NonNull FieldOperator operator, @NonNull EntryField field);

    @NonNull
    MappingRequestSearch or(@NonNull FieldOperator operator, @NonNull EntryField field);

    @NonNull
    default MappingRequestSearch and(@NonNull EntryField field) {
        return and(FieldOperator.EQUAL, field);
    }

    @NonNull
    default MappingRequestSearch or(@NonNull EntryField field) {
        return or(FieldOperator.EQUAL, field);
    }
}
