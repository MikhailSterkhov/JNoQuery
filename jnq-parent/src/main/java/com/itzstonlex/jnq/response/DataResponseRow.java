package com.itzstonlex.jnq.response;

import lombok.NonNull;

public interface DataResponseRow {

    int nextIndex();

    boolean contains(int index);

    boolean contains(@NonNull String id);

    boolean isFirst();

    boolean isLast();

    Object get(int index);

    Object get(@NonNull String id);

    String getString(int index);

    String getString(@NonNull String id);

    Boolean getBoolean(int index);

    Boolean getBoolean(@NonNull String id);

    Long getLong(int index);

    Long getLong(@NonNull String id);

    Integer getInt(int index);

    Integer getInt(@NonNull String id);

    Double getDouble(int index);

    Double getDouble(@NonNull String id);

    Float getFloat(int index);

    Float getFloat(@NonNull String id);

    Short getShort(int index);

    Short getShort(@NonNull String id);

    Byte getByte(int index);

    Byte getByte(@NonNull String id);

    byte[] getByteArray(int index);

    byte[] getByteArray(@NonNull String id);

    void set(int index, @NonNull Object value);

    void set(@NonNull String id, @NonNull Object value);

    void setByteArray(int index, byte[] array);

    void setByteArray(@NonNull String id, byte[] array);
}
