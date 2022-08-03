package com.itzstonlex.jnq.response;

import lombok.NonNull;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

public interface ResponseLine {

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
        return getObject(index).orElseThrow(() -> new NoSuchElementException("no value present"));
    }

    default Object getNullableObject(@NonNull String label) {
        return getObject(label).orElseThrow(() -> new NoSuchElementException("no value present"));
    }

    default String getNullableString(int index) {
        return getString(index).orElseThrow(() -> new NoSuchElementException("no value present"));
    }

    default String getNullableString(@NonNull String label) {
        return getString(label).orElseThrow(() -> new NoSuchElementException("no value present"));
    }

    default Boolean getNullableBoolean(int index) {
        return getBoolean(index).orElseThrow(() -> new NoSuchElementException("no value present"));
    }

    default Boolean getNullableBoolean(@NonNull String label) {
        return getBoolean(label).orElseThrow(() -> new NoSuchElementException("no value present"));
    }

    default Long getNullableLong(int index) {
        return getLong(index).orElseThrow(() -> new NoSuchElementException("no value present"));
    }

    default Long getNullableLong(@NonNull String label) {
        return getLong(label).orElseThrow(() -> new NoSuchElementException("no value present"));
    }

    default Integer getNullableInt(int index) {
        return getInt(index).orElseThrow(() -> new NoSuchElementException("no value present"));
    }

    default Integer getNullableInt(@NonNull String label) {
        return getInt(label).orElseThrow(() -> new NoSuchElementException("no value present"));
    }

    default Double getNullableDouble(int index) {
        return getDouble(index).orElseThrow(() -> new NoSuchElementException("no value present"));
    }

    default Double getNullableDouble(@NonNull String label) {
        return getDouble(label).orElseThrow(() -> new NoSuchElementException("no value present"));
    }

    default Float getNullableFloat(int index) {
        return getFloat(index).orElseThrow(() -> new NoSuchElementException("no value present"));
    }

    default Float getNullableFloat(@NonNull String label) {
        return getFloat(label).orElseThrow(() -> new NoSuchElementException("no value present"));
    }

    default Short getNullableShort(int index) {
        return getShort(index).orElseThrow(() -> new NoSuchElementException("no value present"));
    }

    default Short getNullableShort(@NonNull String label) { 
        return getShort(label).orElseThrow(() -> new NoSuchElementException("no value present"));
    }

    default Byte getNullableByte(int index) {
        return getByte(index).orElseThrow(() -> new NoSuchElementException("no value present"));
    }

    default Byte getNullableByte(@NonNull String label) {
        return getByte(label).orElseThrow(() -> new NoSuchElementException("no value present"));
    }

    default Date getNullableDate(int index) {
        return getDate(index).orElseThrow(() -> new NoSuchElementException("no value present"));
    }

    default Date getNullableDate(@NonNull String label) {
        return getDate(label).orElseThrow(() -> new NoSuchElementException("no value present"));
    }

    default Time getNullableTime(int index) {
        return getTime(index).orElseThrow(() -> new NoSuchElementException("no value present"));
    }

    default Time getNullableTime(@NonNull String label) {
        return getTime(label).orElseThrow(() -> new NoSuchElementException("no value present"));
    }

    default Timestamp getNullableTimestamp(int index) {
        return getTimestamp(index).orElseThrow(() -> new NoSuchElementException("no value present"));
    }

    default Timestamp getNullableTimestamp(@NonNull String label) {
        return getTimestamp(label).orElseThrow(() -> new NoSuchElementException("no value present"));
    }

    default byte[] getNullableBlob(int index) {
        return getBlob(index).orElseThrow(() -> new NoSuchElementException("no value present"));
    }

    default byte[] getNullableBlob(@NonNull String label) {
        return getBlob(label).orElseThrow(() -> new NoSuchElementException("no value present"));
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
        return nextObject().orElseThrow(() -> new NoSuchElementException("no value present"));
    }

    default String nextNullableString() {
        return nextString().orElseThrow(() -> new NoSuchElementException("no value present"));
    }

    default Boolean nextNullableBoolean() {
        return nextBoolean().orElseThrow(() -> new NoSuchElementException("no value present"));
    }

    default Long nextNullableLong() {
        return nextLong().orElseThrow(() -> new NoSuchElementException("no value present"));
    }

    default Integer nextNullableInt() {
        return nextInt().orElseThrow(() -> new NoSuchElementException("no value present"));
    }

    default Double nextNullableDouble() {
        return nextDouble().orElseThrow(() -> new NoSuchElementException("no value present"));
    }

    default Float nextNullableFloat() {
        return nextFloat().orElseThrow(() -> new NoSuchElementException("no value present"));
    }

    default Short nextNullableShort() {
        return nextShort().orElseThrow(() -> new NoSuchElementException("no value present"));
    }

    default Byte nextNullableByte() {
        return nextByte().orElseThrow(() -> new NoSuchElementException("no value present"));
    }

    default Date nextNullableDate() {
        return nextDate().orElseThrow(() -> new NoSuchElementException("no value present"));
    }

    default Time nextNullableTime() {
        return nextTime().orElseThrow(() -> new NoSuchElementException("no value present"));
    }

    default Timestamp nextNullableTimestamp() {
        return nextTimestamp().orElseThrow(() -> new NoSuchElementException("no value present"));
    }

    default byte[] nextNullableBlob() {
        return nextBlob().orElseThrow(() -> new NoSuchElementException("no value present"));
    }
}
