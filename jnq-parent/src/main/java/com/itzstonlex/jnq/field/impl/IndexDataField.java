package com.itzstonlex.jnq.field.impl;

import com.itzstonlex.jnq.field.DataField;
import com.itzstonlex.jnq.field.DataFieldType;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class IndexDataField implements DataField {

    public static IndexDataField create(DataFieldType type, String name) {
        return new IndexDataField(type, name);
    }

    public static IndexDataField createUnique(DataFieldType type, String name) {
        return new IndexDataField(type, name).index(IndexType.UNIQUE);
    }

    public static IndexDataField createNotNull(DataFieldType type, String name) {
        return new IndexDataField(type, name).index(IndexType.NOT_NULL);
    }

    public static IndexDataField createPrimary(DataFieldType type, String name) {
        return new IndexDataField(type, name).index(IndexType.PRIMARY);
    }

    public static IndexDataField createPrimaryNotNull(DataFieldType type, String name) {
        return new IndexDataField(type, name).indexes(IndexType.PRIMARY, IndexType.NOT_NULL);
    }

    public static IndexDataField createPrimaryNotNullAutoIncrement(String name) {
        return new IndexDataField(DataFieldType.INT, name).indexes(IndexType.PRIMARY, IndexType.NOT_NULL, IndexType.AUTO_INCREMENT);
    }

    public static IndexDataField createAutoIncrement(String name) {
        return new IndexDataField(DataFieldType.INT, name).indexes(IndexType.AUTO_INCREMENT);
    }

    private final DataFieldType type;
    private final String name;

    private Object defaultValue;

    private final Collection<IndexType> indexTypes = new ArrayList<>();

    public IndexDataField index(IndexType index) {
        indexTypes.add(index);
        return this;
    }

    public IndexDataField indexes(IndexType... indexes) {
        indexTypes.addAll(Arrays.asList(indexes));
        return this;
    }

    public IndexDataField defaults(Object defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(String.format("`%s`", name));

        stringBuilder.append(" ");
        stringBuilder.append(type.getFormattedName());

        for (IndexType indexType : IndexType.values()) {
            if (!indexTypes.contains(indexType))
                continue;

            stringBuilder.append(" ");
            stringBuilder.append(indexType.getFormattedName());
        }

        if (defaultValue != null) {
            stringBuilder.append(" DEFAULT ").append(defaultValue);
        }

        return stringBuilder.toString();
    }

    @Override
    public String name() {
        return name;
    }

    public enum IndexType {

        NOT_NULL("NOT NULL"),
        PRIMARY("PRIMARY KEY"),
        KEY("KEY"),
        UNIQUE("UNIQUE"),
        FULLTEXT("FULLTEXT"),
        SPATIAL("SPATIAL"),
        AUTO_INCREMENT("AUTO_INCREMENT"),
        ;

        private final String formattedName;

        IndexType(String formattedName) {
            this.formattedName = formattedName;
        }

        public String getFormattedName() {
            return formattedName;
        }
    }
}