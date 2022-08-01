package com.itzstonlex.jnq.impl.response;

import com.itzstonlex.jnq.response.ResponseLine;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

import java.sql.*;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WrapperResponseLine implements ResponseLine {

    ResultSet resultSet;

    @NonFinal
    int currentIndex;

    @Override
    public int nextIndex() {
        if (size() < currentIndex) {
            currentIndex++;
        }

        return currentIndex;
    }

    @SneakyThrows
    @Override
    public int size() {
        return resultSet.getMetaData().getColumnCount();
    }

    @SneakyThrows
    @Override
    public int findIndex(@NonNull String label) {
        return resultSet.findColumn(label);
    }

    @SneakyThrows
    @Override
    public boolean contains(int index) {
        return resultSet.getObject(index) != null;
    }

    @SneakyThrows
    @Override
    public boolean contains(@NonNull String label) {
        return resultSet.getObject(label) != null;
    }

    @SneakyThrows
    @Override
    public boolean isFirstElement() {
        return resultSet.isFirst();
    }

    @SneakyThrows
    @Override
    public boolean isLastElement() {
        return resultSet.isLast();
    }

    @SneakyThrows
    @Override
    public boolean isNullable(int index) {
        return resultSet.getMetaData().isNullable(index) == ResultSetMetaData.columnNoNulls;
    }

    @SneakyThrows
    @Override
    public boolean isNullable(@NonNull String label) {
        return isNullable(findIndex(label));
    }

    @SneakyThrows
    @Override
    public String getLabel(int index) {
        return resultSet.getMetaData().getColumnLabel(index);
    }

    @SneakyThrows
    @Override
    public Object getObject(int index) {
        return resultSet.getObject(index);
    }

    @SneakyThrows
    @Override
    public Object getObject(@NonNull String label) {
        return resultSet.getObject(label);
    }

    @SneakyThrows
    @Override
    public String getString(int index) {
        return resultSet.getString(index);
    }

    @SneakyThrows
    @Override
    public String getString(@NonNull String label) {
        return resultSet.getString(label);
    }

    @SneakyThrows
    @Override
    public Boolean getBoolean(int index) {
        return resultSet.getBoolean(index);
    }

    @SneakyThrows
    @Override
    public Boolean getBoolean(@NonNull String label) {
        return resultSet.getBoolean(label);
    }

    @SneakyThrows
    @Override
    public Long getLong(int index) {
        return resultSet.getLong(index);
    }

    @SneakyThrows
    @Override
    public Long getLong(@NonNull String label) {
        return resultSet.getLong(label);
    }

    @SneakyThrows
    @Override
    public Integer getInt(int index) {
        return resultSet.getInt(index);
    }

    @SneakyThrows
    @Override
    public Integer getInt(@NonNull String label) {
        return resultSet.getInt(label);
    }

    @SneakyThrows
    @Override
    public Double getDouble(int index) {
        return resultSet.getDouble(index);
    }

    @SneakyThrows
    @Override
    public Double getDouble(@NonNull String label) {
        return resultSet.getDouble(label);
    }

    @SneakyThrows
    @Override
    public Float getFloat(int index) {
        return (float) resultSet.getDouble(index);
    }

    @SneakyThrows
    @Override
    public Float getFloat(@NonNull String label) {
        return (float) resultSet.getDouble(label);
    }

    @SneakyThrows
    @Override
    public Short getShort(int index) {
        return resultSet.getShort(index);
    }

    @SneakyThrows
    @Override
    public Short getShort(@NonNull String label) {
        return resultSet.getShort(label);
    }

    @SneakyThrows
    @Override
    public Date getDate(int index) {
        return resultSet.getDate(index);
    }

    @SneakyThrows
    @Override
    public Date getDate(@NonNull String label) {
        return resultSet.getDate(label);
    }

    @SneakyThrows
    @Override
    public Time getTime(int index) {
        return resultSet.getTime(index);
    }

    @SneakyThrows
    @Override
    public Time getTime(@NonNull String label) {
        return resultSet.getTime(label);
    }

    @SneakyThrows
    @Override
    public Timestamp getTimestamp(int index) {
        return resultSet.getTimestamp(index);
    }

    @SneakyThrows
    @Override
    public Timestamp getTimestamp(@NonNull String label) {
        return resultSet.getTimestamp(label);
    }

    @SneakyThrows
    @Override
    public Byte getByte(int index) {
        return resultSet.getByte(index);
    }

    @SneakyThrows
    @Override
    public Byte getByte(@NonNull String label) {
        return resultSet.getByte(label);
    }

    @SneakyThrows
    @Override
    public byte[] getByteArray(int index) {
        return resultSet.getBytes(index);
    }

    @SneakyThrows
    @Override
    public byte[] getByteArray(@NonNull String label) {
        return resultSet.getBytes(label);
    }

    @SneakyThrows
    @Override
    public void set(int index, @NonNull Object value) {
        resultSet.updateObject(index, value);
    }

    @SneakyThrows
    @Override
    public void set(@NonNull String label, @NonNull Object value) {
        resultSet.updateObject(label, value);
    }

    @SneakyThrows
    @Override
    public void setByteArray(int index, byte[] array) {
        resultSet.updateBytes(index, array);
    }

    @SneakyThrows
    @Override
    public void setByteArray(@NonNull String label, byte[] array) {
        resultSet.updateBytes(label, array);
    }

}
