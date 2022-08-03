package com.itzstonlex.jnq.sql.response;

import com.itzstonlex.jnq.response.ResponseLine;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SQLResponseLine extends LinkedHashMap<Integer, Object> implements ResponseLine {

    @Getter
    boolean firstLine, lastLine;

    Supplier<ResponseLine> next;

    @NonFinal
    @Setter(AccessLevel.PACKAGE)
    Set<Integer> nullableIndexes;

    @NonFinal
    @Setter(AccessLevel.PACKAGE)
    Map<String, Integer> indexByLabelsMap;

    @NonFinal
    @Setter(AccessLevel.PACKAGE)
    Map<Integer, String> labelByIndexesMap;

    @NonFinal
    int currentIndex;

    @Override
    public ResponseLine nextResponseLine() {
        return next.get();
    }

    @Override
    public int nextIndex() {
        if (size() > currentIndex) {
            currentIndex++;
        }

        return currentIndex;
    }

    @Override
    public int size() {
        return super.size() / 2;
    }

    @Override
    public int findIndex(@NonNull String label) {
        return indexByLabelsMap.getOrDefault(label.toLowerCase(), -1);
    }

    @SneakyThrows
    @Override
    public String findLabel(int index) {
        return labelByIndexesMap.get(index);
    }

    // *------------------------------------------------- * //

    @Override
    public Set<Integer> getIndexes() {
        return super.keySet();
    }

    @Override
    public Set<String> getLabels() {
        return getIndexes().stream().map(this::findLabel).collect(Collectors.toSet());
    }

    // *------------------------------------------------- * //
    @Override
    public boolean contains(int index) {
        return containsKey(index);
    }

    @Override
    public boolean contains(@NonNull String label) {
        return containsKey(label);
    }

    @SneakyThrows
    @Override
    public boolean isNullable(int index) {
        return nullableIndexes.contains(index);
    }

    @Override
    public boolean isNullable(@NonNull String label) {
        return isNullable(findIndex(label));
    }

    @Override
    public void set(int index, @NonNull Object value) {
        super.put(index, value);
    }

    @Override
    public void set(@NonNull String label, @NonNull Object value) {
        super.put(findIndex(label), value);
    }

    private Optional<Object> lookup(int index) {
        if (index < 0) {
            return Optional.empty();
        }

        return Optional.ofNullable(get(index));
    }

    private <T> Optional<T> lookup(int index, Class<T> cls) {
        return lookup(index).map(cls::cast);
    }

    private <T> Optional<T> lookup(String label, Class<T> cls) {
        return lookup(findIndex(label), cls);
    }

    @Override
    public Optional<Object> getObject(int index) {
        return lookup(index, Object.class);
    }

    @Override
    public Optional<Object> getObject(@NonNull String label) {
        return lookup(label, Object.class);
    }

    @Override
    public Optional<String> getString(int index) {
        return lookup(index, String.class);
    }

    @Override
    public Optional<String> getString(@NonNull String label) {
        return lookup(label, String.class);
    }

    @Override
    public Optional<Boolean> getBoolean(int index) {
        return lookup(index, Boolean.class);
    }

    @Override
    public Optional<Boolean> getBoolean(@NonNull String label) {
        return lookup(label, Boolean.class);
    }

    @Override
    public Optional<Long> getLong(int index) {
        return lookup(index, Number.class).map(Number::longValue);
    }

    @Override
    public Optional<Long> getLong(@NonNull String label) {
        return lookup(label, Number.class).map(Number::longValue);
    }

    @Override
    public Optional<Integer> getInt(int index) {
        return lookup(index, Number.class).map(Number::intValue);
    }

    @Override
    public Optional<Integer> getInt(@NonNull String label) {
        return lookup(label, Number.class).map(Number::intValue);
    }

    @Override
    public Optional<Double> getDouble(int index) {
        return lookup(index, Number.class).map(Number::doubleValue);
    }

    @Override
    public Optional<Double> getDouble(@NonNull String label) {
        return lookup(label, Number.class).map(Number::doubleValue);
    }

    @Override
    public Optional<Float> getFloat(int index) {
        return lookup(index, Number.class).map(Number::floatValue);
    }

    @Override
    public Optional<Float> getFloat(@NonNull String label) {
        return lookup(label, Number.class).map(Number::floatValue);
    }

    @Override
    public Optional<Short> getShort(int index) {
        return lookup(index, Number.class).map(Number::shortValue);
    }

    @Override
    public Optional<Short> getShort(@NonNull String label) {
        return lookup(label, Number.class).map(Number::shortValue);
    }

    @Override
    public Optional<Byte> getByte(int index) {
        return lookup(index, Number.class).map(Number::byteValue);
    }

    @Override
    public Optional<Byte> getByte(@NonNull String label) {
        return lookup(label, Number.class).map(Number::byteValue);
    }

    @Override
    public Optional<Date> getDate(int index) {
        return lookup(index, Date.class);
    }

    @Override
    public Optional<Date> getDate(@NonNull String label) {
        return lookup(label, Date.class);
    }

    @Override
    public Optional<Time> getTime(int index) {
        return lookup(index, Time.class);
    }

    @Override
    public Optional<Time> getTime(@NonNull String label) {
        return lookup(label, Time.class);
    }

    @Override
    public Optional<Timestamp> getTimestamp(int index) {
        return lookup(index, Timestamp.class);
    }

    @Override
    public Optional<Timestamp> getTimestamp(@NonNull String label) {
        return lookup(label, Timestamp.class);
    }

    @Override
    public Optional<byte[]> getBlob(int index) {
        return lookup(index, byte[].class);
    }

    @Override
    public Optional<byte[]> getBlob(@NonNull String label) {
        return lookup(label, byte[].class);
    }

}
