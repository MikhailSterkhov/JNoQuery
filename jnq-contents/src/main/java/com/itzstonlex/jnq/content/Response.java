package com.itzstonlex.jnq.content;

import lombok.NonNull;

import java.util.List;

public interface Response extends List<ResponseLine> {

    ResponseLine getFirst();

    ResponseLine getLast();

    default void addAll(@NonNull Response response) {
        response.forEach(this::add);
    }
}
