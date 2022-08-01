package com.itzstonlex.jnq.response;

import lombok.NonNull;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

public interface ResponseLine {

    int nextIndex();
    
    int size();

    int findIndex(@NonNull String label);

    boolean contains(int index);

    boolean contains(@NonNull String label);

    boolean isFirstElement();

    boolean isLastElement();
    
    boolean isNullable(int index);

    boolean isNullable(@NonNull String label);
    
    String getLabel(int index);

    Object getObject(int index);

    Object getObject(@NonNull String label);

    String getString(int index);

    String getString(@NonNull String label);

    Boolean getBoolean(int index);

    Boolean getBoolean(@NonNull String label);

    Long getLong(int index);

    Long getLong(@NonNull String label);

    Integer getInt(int index);

    Integer getInt(@NonNull String label);

    Double getDouble(int index);

    Double getDouble(@NonNull String label);

    Float getFloat(int index);

    Float getFloat(@NonNull String label);

    Short getShort(int index);

    Short getShort(@NonNull String label);

    Byte getByte(int index);

    Byte getByte(@NonNull String label);

    Date getDate(int index);

    Date getDate(@NonNull String label);

    Time getTime(int index);

    Time getTime(@NonNull String label);

    Timestamp getTimestamp(int index);

    Timestamp getTimestamp(@NonNull String label);

    byte[] getByteArray(int index);

    byte[] getByteArray(@NonNull String label);

    void set(int index, @NonNull Object value);

    void set(@NonNull String label, @NonNull Object value);

    void setByteArray(int index, byte[] array);

    void setByteArray(@NonNull String label, byte[] array);

    default Object nextObject() {
        return getObject(nextIndex());
    }

    default String nextString() {
        return getString(nextIndex());
    }

    default Boolean nextBoolean() {
        return getBoolean(nextIndex());
    }

    default Long nextLong() {
        return getLong(nextIndex());
    }

    default Integer nextInt() {
        return getInt(nextIndex());
    }

    default Double nextDouble() {
        return getDouble(nextIndex());
    }

    default Float nextFloat() {
        return getFloat(nextIndex());
    }

    default Object nextShort() {
        return getObject(nextIndex());
    }

    default Byte nextByte() {
        return getByte(nextIndex());
    }

    default Date nextDate() {
        return getDate(nextIndex());
    }

    default Time nextTime() {
        return getTime(nextIndex());
    }

    default Timestamp nextTimestamp() {
        return getTimestamp(nextIndex());
    }

    default byte[] nextByteArray() {
        return getByteArray(nextIndex());
    }
}
