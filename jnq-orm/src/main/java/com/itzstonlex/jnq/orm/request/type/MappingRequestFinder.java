package com.itzstonlex.jnq.orm.request.type;

import com.itzstonlex.jnq.content.field.FieldOperator;
import com.itzstonlex.jnq.content.field.type.EntryField;
import com.itzstonlex.jnq.orm.request.MappingRequest;
import lombok.NonNull;

import java.util.Map;

public interface MappingRequestFinder extends MappingRequest {

    int limit();

    @NonNull
    Map<EntryField, FieldOperator> entryFieldsAnd();

    @NonNull
    Map<EntryField, FieldOperator> entryFieldsOr();

    @NonNull
    MappingRequestFinder limit(int limit);

    @NonNull
    MappingRequestFinder and(@NonNull FieldOperator operator, @NonNull EntryField field);

    @NonNull
    MappingRequestFinder or(@NonNull FieldOperator operator, @NonNull EntryField field);

    @NonNull
    default MappingRequestFinder and(@NonNull EntryField field) {
        return and(FieldOperator.EQUAL, field);
    }

    @NonNull
    default MappingRequestFinder or(@NonNull EntryField field) {
        return or(FieldOperator.EQUAL, field);
    }
}
