package com.itzstonlex.jnq.impl.wrapper;

import com.itzstonlex.jnq.response.DataResponseRow;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

import java.sql.ResultSet;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WrapperDataResponseRow implements DataResponseRow {

    ResultSet resultSet;

    @NonFinal
    int currentIndex;

    @SneakyThrows
    @Override
    public int nextIndex() {
        if (resultSet.getMetaData().getColumnCount() < currentIndex) {
            currentIndex++;
        }

        return currentIndex;
    }

    @SneakyThrows
    @Override
    public boolean contains(int index) {
        return resultSet.getObject(index) != null;
    }

    @SneakyThrows
    @Override
    public boolean contains(@NonNull String id) {
        return resultSet.getObject(id) != null;
    }

    @SneakyThrows
    @Override
    public boolean isFirst() {
        return resultSet.isFirst();
    }

    @SneakyThrows
    @Override
    public boolean isLast() {
        return resultSet.isLast();
    }

    @SneakyThrows
    @Override
    public Object get(int index) {
        return resultSet.getObject(index);
    }

    @SneakyThrows
    @Override
    public Object get(@NonNull String id) {
        return resultSet.getObject(id);
    }

    @SneakyThrows
    @Override
    public String getString(int index) {
        return resultSet.getString(index);
    }

    @SneakyThrows
    @Override
    public String getString(@NonNull String id) {
        return resultSet.getString(id);
    }

    @SneakyThrows
    @Override
    public Boolean getBoolean(int index) {
        return resultSet.getBoolean(index);
    }

    @SneakyThrows
    @Override
    public Boolean getBoolean(@NonNull String id) {
        return resultSet.getBoolean(id);
    }

    @SneakyThrows
    @Override
    public Long getLong(int index) {
        return resultSet.getLong(index);
    }

    @SneakyThrows
    @Override
    public Long getLong(@NonNull String id) {
        return resultSet.getLong(id);
    }

    @SneakyThrows
    @Override
    public Integer getInt(int index) {
        return resultSet.getInt(index);
    }

    @SneakyThrows
    @Override
    public Integer getInt(@NonNull String id) {
        return resultSet.getInt(id);
    }

    @SneakyThrows
    @Override
    public Double getDouble(int index) {
        return resultSet.getDouble(index);
    }

    @SneakyThrows
    @Override
    public Double getDouble(@NonNull String id) {
        return resultSet.getDouble(id);
    }

    @SneakyThrows
    @Override
    public Float getFloat(int index) {
        return (float) resultSet.getDouble(index);
    }

    @SneakyThrows
    @Override
    public Float getFloat(@NonNull String id) {
        return (float) resultSet.getDouble(id);
    }

    @SneakyThrows
    @Override
    public Short getShort(int index) {
        return resultSet.getShort(index);
    }

    @SneakyThrows
    @Override
    public Short getShort(@NonNull String id) {
        return resultSet.getShort(id);
    }

    @SneakyThrows
    @Override
    public Byte getByte(int index) {
        return resultSet.getByte(index);
    }

    @SneakyThrows
    @Override
    public Byte getByte(@NonNull String id) {
        return resultSet.getByte(id);
    }

    @SneakyThrows
    @Override
    public byte[] getByteArray(int index) {
        return resultSet.getBytes(index);
    }

    @SneakyThrows
    @Override
    public byte[] getByteArray(@NonNull String id) {
        return resultSet.getBytes(id);
    }

    @SneakyThrows
    @Override
    public void set(int index, @NonNull Object value) {
        resultSet.updateObject(index, value);
    }

    @SneakyThrows
    @Override
    public void set(@NonNull String id, @NonNull Object value) {
        resultSet.updateObject(id, value);
    }

    @SneakyThrows
    @Override
    public void setByteArray(int index, byte[] array) {
        resultSet.updateBytes(index, array);
    }

    @SneakyThrows
    @Override
    public void setByteArray(@NonNull String id, byte[] array) {
        resultSet.updateBytes(id, array);
    }

}
