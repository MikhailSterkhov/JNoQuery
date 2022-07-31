package com.itzstonlex.jnq.field.request;

import com.itzstonlex.jnq.field.DataField;
import lombok.NonNull;

public interface FieldSetRequest<R, F extends DataField> {

    @SuppressWarnings("unchecked")
    @NonNull
    R set(F... fields);
}
