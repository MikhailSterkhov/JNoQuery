package com.itzstonlex.jnq.sql.response;

import com.itzstonlex.jnq.exception.JnqException;
import com.itzstonlex.jnq.response.Response;
import com.itzstonlex.jnq.response.ResponseLine;
import lombok.NonNull;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Supplier;

public class SQLResponse extends LinkedList<ResponseLine> implements Response {

    public SQLResponse(@NonNull Response response) {
        super(response);
    }

    public SQLResponse(@NonNull ResultSet resultSet)
    throws JnqException {

        try {
            ResultSetMetaData metadata = resultSet.getMetaData();

            int columns = metadata.getColumnCount();
            int index = 0;

            while (resultSet.next()) {

                int finalIndex = index; // java8 fuck u
                Supplier<ResponseLine> nextSupplier = () -> super.get(finalIndex);

                Map<String, Integer> indexByLabelsMap = new HashMap<>();
                Map<Integer, String> labelByIndexesMap = new HashMap<>();

                Set<Integer> nullableIndexes = new HashSet<>();

                SQLResponseLine responseLine = new SQLResponseLine(index == 0, index == (columns - 1), nextSupplier);

                for (int columnIndex = 1; columnIndex <= columns; columnIndex++) {

                    String name = metadata.getColumnName(columnIndex);

                    indexByLabelsMap.put(name.toLowerCase(), columnIndex);
                    labelByIndexesMap.put(columnIndex, name);

                    if (metadata.isNullable(columnIndex) == ResultSetMetaData.columnNullable) {
                        nullableIndexes.add(columnIndex);
                    }

                    responseLine.set(columnIndex, resultSet.getObject(columnIndex));
                }

                responseLine.setIndexByLabelsMap(indexByLabelsMap);
                responseLine.setLabelByIndexesMap(labelByIndexesMap);

                responseLine.setNullableIndexes(nullableIndexes);

                super.add(responseLine);
                index++;
            }
        }
        catch (SQLException exception) {
            throw new JnqException("response", exception);
        }
    }

}
