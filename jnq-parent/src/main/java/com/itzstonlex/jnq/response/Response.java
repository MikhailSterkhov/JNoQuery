package com.itzstonlex.jnq.response;

import lombok.NonNull;

import java.util.Collection;

public interface Response extends Collection<ResponseLine> {

    ResponseLine getFirst();

    ResponseLine getLast();

    default void addAll(@NonNull Response response) {
        response.forEach(this::add);
    }
}
