package com.itzstonlex.jnq.response;

import lombok.NonNull;

import java.util.List;

public interface Response extends List<ResponseLine> {

    ResponseLine getFirst();

    ResponseLine getLast();

    default void addAll(@NonNull Response response) {
        response.forEach(this::add);
    }
}
