package com.itzstonlex.jnq.content.field;

import lombok.NonNull;

public interface DataField {

    @NonNull
    String name();

    @NonNull
    @Override
    String toString();
}
