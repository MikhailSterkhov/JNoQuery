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
        int current = nextIndex();
        return contains(current) ? getString(current) : null;
    }

    default Boolean nextBoolean() {
        int current = nextIndex();
        return contains(current) ? getBoolean(current) : null;
    }

    default Long nextLong() {
        int current = nextIndex();
        return contains(current) ? getLong(current) : null;
    }

    default Integer nextInt() {
        int current = nextIndex();
        return contains(current) ? getInt(current) : null;
    }

    default Double nextDouble() {
        int current = nextIndex();
        return contains(current) ? getDouble(current) : null;
    }

    default Float nextFloat() {
        int current = nextIndex();
        return contains(current) ? getFloat(current) : null;
    }

    default Object nextShort() {
        int current = nextIndex();
        return contains(current) ? getShort(current) : null;
    }

    default Byte nextByte() {
        int current = nextIndex();
        return contains(current) ? getByte(current) : null;
    }

    default Date nextDate() {
        int current = nextIndex();
        return contains(current) ? getDate(current) : null;
    }

    default Time nextTime() {
        int current = nextIndex();
        return contains(current) ? getTime(current) : null;
    }

    default Timestamp nextTimestamp() {
        int current = nextIndex();
        return contains(current) ? getTimestamp(current) : null;
    }

    default byte[] nextByteArray() {
        int current = nextIndex();
        return contains(current) ? getByteArray(current) : null;
    }
}
