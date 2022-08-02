package com.itzstonlex.jnq.field;

import lombok.NonNull;

public interface DataField {

    @NonNull
    String name();

    @NonNull
    @Override
    String toString();
}
