package com.itzstonlex.jnq.content.field.type;

import com.itzstonlex.jnq.content.field.DataField;
import com.itzstonlex.jnq.content.field.FieldType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class IndexField implements DataField {

    public static @NonNull IndexField create(@NonNull FieldType type, @NonNull String name) {
        return new IndexField(type, name);
    }

    public static @NonNull IndexField createUnique(@NonNull FieldType type, @NonNull String name) {
        return new IndexField(type, name).index(IndexType.UNIQUE);
    }

    public static @NonNull IndexField createNotNull(@NonNull FieldType type, @NonNull String name) {
        return new IndexField(type, name).index(IndexType.NOT_NULL);
    }

    public static @NonNull IndexField createPrimary(@NonNull FieldType type, @NonNull String name) {
        return new IndexField(type, name).index(IndexType.PRIMARY);
    }

    public static @NonNull IndexField createPrimaryNotNull(@NonNull FieldType type, @NonNull String name) {
        return new IndexField(type, name).indexes(IndexType.PRIMARY, IndexType.NOT_NULL);
    }

    public static @NonNull IndexField createPrimaryNotNullAutoIncrement(@NonNull String name) {
        return new IndexField(FieldType.INT, name).indexes(IndexType.PRIMARY, IndexType.NOT_NULL, IndexType.AUTO_INCREMENT);
    }

    public static @NonNull IndexField createAutoIncrement(@NonNull String name) {
        return new IndexField(FieldType.INT, name).indexes(IndexType.AUTO_INCREMENT);
    }

    Set<IndexType> indexTypes = new HashSet<>();

    FieldType type;
    String name;

    @NonFinal
    Object defaultValue;

    public @NonNull IndexField index(@NonNull IndexType index) {
        indexTypes.add(index);
        return this;
    }

    public @NonNull IndexField indexes(@NonNull IndexType... indexes) {
        indexTypes.addAll(Arrays.asList(indexes));
        return this;
    }

    public @NonNull IndexField defaults(@NonNull Object defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }

    public boolean has(@NonNull IndexType index) {
        return indexTypes.contains(index);
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