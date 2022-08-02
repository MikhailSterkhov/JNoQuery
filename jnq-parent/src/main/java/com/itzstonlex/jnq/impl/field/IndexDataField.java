package com.itzstonlex.jnq.impl.field;

import com.itzstonlex.jnq.field.DataField;
import com.itzstonlex.jnq.field.FieldType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

import java.util.*;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class IndexDataField implements DataField {

    public static @NonNull IndexDataField create(@NonNull FieldType type, @NonNull String name) {
        return new IndexDataField(type, name);
    }

    public static @NonNull IndexDataField createUnique(@NonNull FieldType type, @NonNull String name) {
        return new IndexDataField(type, name).index(IndexType.UNIQUE);
    }

    public static @NonNull IndexDataField createNotNull(@NonNull FieldType type, @NonNull String name) {
        return new IndexDataField(type, name).index(IndexType.NOT_NULL);
    }

    public static @NonNull IndexDataField createPrimary(@NonNull FieldType type, @NonNull String name) {
        return new IndexDataField(type, name).index(IndexType.PRIMARY);
    }

    public static @NonNull IndexDataField createPrimaryNotNull(@NonNull FieldType type, @NonNull String name) {
        return new IndexDataField(type, name).indexes(IndexType.PRIMARY, IndexType.NOT_NULL);
    }

    public static @NonNull IndexDataField createPrimaryNotNullAutoIncrement(@NonNull String name) {
        return new IndexDataField(FieldType.INT, name).indexes(IndexType.PRIMARY, IndexType.NOT_NULL, IndexType.AUTO_INCREMENT);
    }

    public static @NonNull IndexDataField createAutoIncrement(@NonNull String name) {
        return new IndexDataField(FieldType.INT, name).indexes(IndexType.AUTO_INCREMENT);
    }

    Set<IndexType> indexTypes = new HashSet<>();

    FieldType type;
    String name;

    @NonFinal
    Object defaultValue;

    public @NonNull IndexDataField index(@NonNull IndexType index) {
        indexTypes.add(index);
        return this;
    }

    public @NonNull IndexDataField indexes(@NonNull IndexType... indexes) {
        indexTypes.addAll(Arrays.asList(indexes));
        return this;
    }

    public @NonNull IndexDataField defaults(@NonNull Object defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(String.format("`%s`", name));

        stringBuilder.append(" ");
        stringBuilder.append(type.getSql());

        for (IndexType indexType : IndexType.values()) {
            if (!indexTypes.contains(indexType))
                continue;

            stringBuilder.append(" ");
            stringBuilder.append(indexType.getSql());
        }

        if (defaultValue != null) {
            stringBuilder.append(" DEFAULT ").append(defaultValue);
        }

        return stringBuilder.toString();
    }

    @Override
    public @NonNull String name() {
        return name;
    }

    public @NonNull FieldType type() {
        return type;
    }

    @Getter
    @RequiredArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
    public enum IndexType {

        NOT_NULL("NOT NULL"),
        PRIMARY("PRIMARY KEY"),
        KEY("KEY"),
        UNIQUE("UNIQUE"),
        FULLTEXT("FULLTEXT"),
        SPATIAL("SPATIAL"),
        AUTO_INCREMENT("AUTO_INCREMENT"),
        ;

        String sql;
    }
}