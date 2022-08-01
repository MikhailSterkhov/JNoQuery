package com.itzstonlex.jnq.field;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.sql.Blob;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.Year;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum FieldType {

    TINY_INT("TINYINT", Character.class),
    SMALL_INT("SMALLINT", Byte.class),
    MEDIUM_INT("MEDIUMINT", Short.class),
    INT("INT", Integer.class),
    BIG_INT("BIGINT", Long.class),
    BIT("BIT", Integer.class),
    BOOLEAN("BOOLEAN", Boolean.class),

    FLOAT("FLOAT", Float.class),
    DOUBLE("DOUBLE", Double.class),
    DECIMAL("DECIMAL", Integer.class),

    CHAR("CHAR", Character.class),
    VAR_CHAR("VARCHAR", String.class),
    TINY_TEXT("TINYTEXT", CharSequence.class),
    TEXT("TEXT", String.class),
    MEDIUM_TEXT("MEDIUMTEXT", CharSequence.class),
    LONG_TEXT("LONGTEXT", String.class),
    JSON("JSON", String.class),

    BINARY("BINARY", byte[].class),
    VAR_BINARY("VARBINARY", byte[].class),
    TINY_BLOB("TINYBLOB", Blob.class),
    BLOB("BLOB", Blob.class),
    MEDIUM_BLOB("MEDIUMBLOB", Blob.class),
    LONG_BLOB("LONGBLOB", Blob.class),

    DATE("DATE", Date.class),
    TIME("TIME", Time.class),
    YEAR("YEAR", Year.class),
    DATETIME("DATETIME", Date.class),
    TIMESTAMP("TIMESTAMP", Timestamp.class),

    UNKNOWN("UNKNOWN", Object.class),
    ;

    public static final FieldType[] VALUES = FieldType.values();

    public static FieldType fromAttachment(@NonNull Class<?> attachment) {
        for (FieldType fieldType : VALUES) {

            if (fieldType.cls.isAssignableFrom(attachment)) {
                return fieldType;
            }
        }

        return UNKNOWN;
    }

    String sql;
    Class<?> cls;
}