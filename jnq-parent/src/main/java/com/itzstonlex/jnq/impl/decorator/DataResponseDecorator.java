package com.itzstonlex.jnq.impl.decorator;

import com.itzstonlex.jnq.impl.wrapper.WrapperDataResponseRow;
import com.itzstonlex.jnq.response.DataResponse;
import com.itzstonlex.jnq.response.DataResponseRow;
import lombok.NonNull;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.util.LinkedList;

public class DataResponseDecorator extends LinkedList<DataResponseRow> implements DataResponse {

    public DataResponseDecorator(@NonNull DataResponse response) {
        super(response);
    }

    @SneakyThrows
    public DataResponseDecorator(@NonNull ResultSet resultSet) {
        while (resultSet.next()) {
            add(new WrapperDataResponseRow(resultSet));
        }
    }

}
