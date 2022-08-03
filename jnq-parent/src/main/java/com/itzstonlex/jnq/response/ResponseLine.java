package com.itzstonlex.jnq.response;

import lombok.NonNull;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

public interface ResponseLine {
    
    @NonNull
    Supplier<NoSuchElementException> NO_VALUE_PRESENT_SUPPLIER = (() -> new NoSuchElementException("no value present"));

    // *------------------------------------------------- * //

    ResponseLine nextResponseLine();

    int nextIndex();

    int size();

    int findIndex(@NonNull String label);

    String findLabel(int index);

    // *------------------------------------------------- * //

    Set<Integer> getIndexes();
    Set<String> getLabels();

    // *------------------------------------------------- * //

    boolean contains(int index);

    boolean contains(@NonNull String label);

    boolean isFirstLine();

    boolean isLastLine();

    boolean isNullable(int index);

    boolean isNullable(@NonNull String label);

    // *------------------------------------------------- * //

    void set(int index, Object value);

    void set(@NonNull String label, Object value);

    // *------------------------------------------------- * //

    Optional<Object> getObject(int index);

    Optional<Object> getObject(@NonNull String label);

    Optional<String> getString(int index);

    Optional<String> getString(@NonNull String label);

    Optional<Boolean> getBoolean(int index);

    Optional<Boolean> getBoolean(@NonNull String label);

    Optional<Long> getLong(int index);

    Optional<Long> getLong(@NonNull String label);

    Optional<Integer> getInt(int index);

    Optional<Integer> getInt(@NonNull String label);

    Optional<Double> getDouble(int index);

    Optional<Double> getDouble(@NonNull String label);

    Optional<Float> getFloat(int index);

    Optional<Float> getFloat(@NonNull String label);

    Optional<Short> getShort(int index);

    Optional<Short> getShort(@NonNull String label);

    Optional<Byte> getByte(int index);

    Optional<Byte> getByte(@NonNull String label);

    Optional<Date> getDate(int index);

    Optional<Date> getDate(@NonNull String label);

    Optional<Time> getTime(int index);

    Optional<Time> getTime(@NonNull String label);

    Optional<Timestamp> getTimestamp(int index);

    Optional<Timestamp> getTimestamp(@NonNull String label);

    Optional<byte[]> getBlob(int index);

    Optional<byte[]> getBlob(@NonNull String label);

    // *------------------------------------------------- * //

    default Object getNullableObject(int index) {
        return getObject(index).orElseThrow(NO_VALUE_PRESENT_SUPPLIER);
    }

    default Object getNullableObject(@NonNull String label) {
        return getObject(label).orElseThrow(NO_VALUE_PRESENT_SUPPLIER);
    }

    default String getNullableString(int index) {
        return getString(index).orElseThrow(NO_VALUE_PRESENT_SUPPLIER);
    }

    default String getNullableString(@NonNull String label) {
        return getString(label).orElseThrow(NO_VALUE_PRESENT_SUPPLIER);
    }

    default Boolean getNullableBoolean(int index) {
        return getBoolean(index).orElseThrow(NO_VALUE_PRESENT_SUPPLIER);
    }

    default Boolean getNullableBoolean(@NonNull String label) {
        return getBoolean(label).orElseThrow(NO_VALUE_PRESENT_SUPPLIER);
    }

    default Long getNullableLong(int index) {
        return getLong(index).orElseThrow(NO_VALUE_PRESENT_SUPPLIER);
    }

    default Long getNullableLong(@NonNull String label) {
        return getLong(label).orElseThrow(NO_VALUE_PRESENT_SUPPLIER);
    }

    default Integer getNullableInt(int index) {
        return getInt(index).orElseThrow(NO_VALUE_PRESENT_SUPPLIER);
    }

    default Integer getNullableInt(@NonNull String label) {
        return getInt(label).orElseThrow(NO_VALUE_PRESENT_SUPPLIER);
    }

    default Double getNullableDouble(int index) {
        return getDouble(index).orElseThrow(NO_VALUE_PRESENT_SUPPLIER);
    }

    default Double getNullableDouble(@NonNull String label) {
        return getDouble(label).orElseThrow(NO_VALUE_PRESENT_SUPPLIER);
    }

    default Float getNullableFloat(int index) {
        return getFloat(index).orElseThrow(NO_VALUE_PRESENT_SUPPLIER);
    }

    default Float getNullableFloat(@NonNull String label) {
        return getFloat(label).orElseThrow(NO_VALUE_PRESENT_SUPPLIER);
    }

    default Short getNullableShort(int index) {
        return getShort(index).orElseThrow(NO_VALUE_PRESENT_SUPPLIER);
    }

    default Short getNullableShort(@NonNull String label) {
        return getShort(label).orElseThrow(NO_VALUE_PRESENT_SUPPLIER);
    }

    default Byte getNullableByte(int index) {
        return getByte(index).orElseThrow(NO_VALUE_PRESENT_SUPPLIER);
    }

    default Byte getNullableByte(@NonNull String label) {
        return getByte(label).orElseThrow(NO_VALUE_PRESENT_SUPPLIER);
    }

    default Date getNullableDate(int index) {
        return getDate(index).orElseThrow(NO_VALUE_PRESENT_SUPPLIER);
    }

    default Date getNullableDate(@NonNull String label) {
        return getDate(label).orElseThrow(NO_VALUE_PRESENT_SUPPLIER);
    }

    default Time getNullableTime(int index) {
        return getTime(index).orElseThrow(NO_VALUE_PRESENT_SUPPLIER);
    }

    default Time getNullableTime(@NonNull String label) {
        return getTime(label).orElseThrow(NO_VALUE_PRESENT_SUPPLIER);
    }

    default Timestamp getNullableTimestamp(int index) {
        return getTimestamp(index).orElseThrow(NO_VALUE_PRESENT_SUPPLIER);
    }

    default Timestamp getNullableTimestamp(@NonNull String label) {
        return getTimestamp(label).orElseThrow(NO_VALUE_PRESENT_SUPPLIER);
    }

    default byte[] getNullableBlob(int index) {
        return getBlob(index).orElseThrow(NO_VALUE_PRESENT_SUPPLIER);
    }

    default byte[] getNullableBlob(@NonNull String label) {
        return getBlob(label).orElseThrow(NO_VALUE_PRESENT_SUPPLIER);
    }

    // *------------------------------------------------- * //

    default Optional<Object> nextObject() {
        return getObject(nextIndex());
    }

    default Optional<String> nextString() {
        int current = nextIndex();
        return contains(current) ? getString(current) : Optional.empty();
    }

    default Optional<Boolean> nextBoolean() {
        int current = nextIndex();
        return contains(current) ? getBoolean(current) : Optional.empty();
    }

    default Optional<Long> nextLong() {
        int current = nextIndex();
        return contains(current) ? getLong(current) : Optional.empty();
    }

    default Optional<Integer> nextInt() {
        int current = nextIndex();
        return contains(current) ? getInt(current) : Optional.empty();
    }

    default Optional<Double> nextDouble() {
        int current = nextIndex();
        return contains(current) ? getDouble(current) : Optional.empty();
    }

    default Optional<Float> nextFloat() {
        int current = nextIndex();
        return contains(current) ? getFloat(current) : Optional.empty();
    }

    default Optional<Short> nextShort() {
        int current = nextIndex();
        return contains(current) ? getShort(current) : Optional.empty();
    }

    default Optional<Byte> nextByte() {
        int current = nextIndex();
        return contains(current) ? getByte(current) : Optional.empty();
    }

    default Optional<Date> nextDate() {
        int current = nextIndex();
        return contains(current) ? getDate(current) : Optional.empty();
    }

    default Optional<Time> nextTime() {
        int current = nextIndex();
        return contains(current) ? getTime(current) : Optional.empty();
    }

    default Optional<Timestamp> nextTimestamp() {
        int current = nextIndex();
        return contains(current) ? getTimestamp(current) : Optional.empty();
    }

    default Optional<byte[]> nextBlob() {
        int current = nextIndex();
        return contains(current) ? getBlob(current) : Optional.empty();
    }

    // *------------------------------------------------- * //

    default Object nextNullableObject() {
        return nextObject().orElseThrow(NO_VALUE_PRESENT_SUPPLIER);
    }

    default String nextNullableString() {
        return nextString().orElseThrow(NO_VALUE_PRESENT_SUPPLIER);
    }

    default Boolean nextNullableBoolean() {
        return nextBoolean().orElseThrow(NO_VALUE_PRESENT_SUPPLIER);
    }

    default Long nextNullableLong() {
        return nextLong().orElseThrow(NO_VALUE_PRESENT_SUPPLIER);
    }

    default Integer nextNullableInt() {
        return nextInt().orElseThrow(NO_VALUE_PRESENT_SUPPLIER);
    }

    default Double nextNullableDouble() {
        return nextDouble().orElseThrow(NO_VALUE_PRESENT_SUPPLIER);
    }

    default Float nextNullableFloat() {
        return nextFloat().orElseThrow(NO_VALUE_PRESENT_SUPPLIER);
    }

    default Short nextNullableShort() {
        return nextShort().orElseThrow(NO_VALUE_PRESENT_SUPPLIER);
    }

    default Byte nextNullableByte() {
        return nextByte().orElseThrow(NO_VALUE_PRESENT_SUPPLIER);
    }

    default Date nextNullableDate() {
        return nextDate().orElseThrow(NO_VALUE_PRESENT_SUPPLIER);
    }

    default Time nextNullableTime() {
        return nextTime().orElseThrow(NO_VALUE_PRESENT_SUPPLIER);
    }

    default Timestamp nextNullableTimestamp() {
        return nextTimestamp().orElseThrow(NO_VALUE_PRESENT_SUPPLIER);
    }

    default byte[] nextNullableBlob() {
        return nextBlob().orElseThrow(NO_VALUE_PRESENT_SUPPLIER);
    }
}
