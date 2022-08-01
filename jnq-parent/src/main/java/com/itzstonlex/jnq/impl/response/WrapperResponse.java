package com.itzstonlex.jnq.impl.response;

import com.itzstonlex.jnq.response.Response;
import com.itzstonlex.jnq.response.ResponseLine;
import lombok.NonNull;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.util.LinkedList;

public class WrapperResponse extends LinkedList<ResponseLine> implements Response {

    public WrapperResponse(@NonNull Response response) {
        super(response);
    }

    @SneakyThrows
    public WrapperResponse(@NonNull ResultSet resultSet) {
        while (resultSet.next()) {
            add(new WrapperResponseLine(resultSet));
        }
    }

}
