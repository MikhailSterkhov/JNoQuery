package com.itzstonlex.jnq.response;

import java.util.Collection;

public interface DataResponse extends Collection<DataResponseRow> {

    DataResponseRow getFirst();

    DataResponseRow getLast();

    default void addAll(DataResponse response) {
        response.forEach(this::add);
    }
}
