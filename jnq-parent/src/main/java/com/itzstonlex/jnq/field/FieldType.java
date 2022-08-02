package com.itzstonlex.jnq.field;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.sql.*;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum FieldType {

    TINYINT(JDBCType.TINYINT.getVendorTypeNumber(), "TINYINT", Character.class),
    SMALLINT(JDBCType.SMALLINT.getVendorTypeNumber(), "SMALLINT", Byte.class),
    MEDIUM_INT(JDBCType.INTEGER.getVendorTypeNumber(), "MEDIUMINT", Short.class),
    INT(JDBCType.INTEGER.getVendorTypeNumber(), "INT", Integer.class),
    BIGINT(JDBCType.BIT.getVendorTypeNumber(), "BIGINT", Long.class),
    BIT(JDBCType.BIT.getVendorTypeNumber(), "BIT", Integer.class),
    BOOLEAN(JDBCType.BOOLEAN.getVendorTypeNumber(), "BOOLEAN", Boolean.class),

    FLOAT(JDBCType.FLOAT.getVendorTypeNumber(), "FLOAT", Float.class),
    DOUBLE(JDBCType.DOUBLE.getVendorTypeNumber(), "DOUBLE", Double.class),
    LONG(JDBCType.BIGINT.getVendorTypeNumber(), "LONG", Long.class),
    DECIMAL(JDBCType.DECIMAL.getVendorTypeNumber(), "DECIMAL", Integer.class),

    CHAR(JDBCType.CHAR.getVendorTypeNumber(), "CHAR", Character.class),
    VARCHAR(JDBCType.VARCHAR.getVendorTypeNumber(), "VARCHAR", String.class),
    TINYTEXT(JDBCType.VARCHAR.getVendorTypeNumber(), "TINYTEXT", CharSequence.class),
    TEXT(JDBCType.VARCHAR.getVendorTypeNumber(), "TEXT", String.class),
    MEDIUM_TEXT(JDBCType.NVARCHAR.getVendorTypeNumber(), "MEDIUMTEXT", CharSequence.class),
    LONGTEXT(JDBCType.LONGNVARCHAR.getVendorTypeNumber(), "LONGTEXT", String.class),

    BINARY(JDBCType.BINARY.getVendorTypeNumber(), "BINARY", byte[].class),
    VAR_BINARY(JDBCType.VARBINARY.getVendorTypeNumber(), "VARBINARY", byte[].class),
    TINY_BLOB(JDBCType.BLOB.getVendorTypeNumber(), "TINYBLOB", Blob.class),
    BLOB(JDBCType.BLOB.getVendorTypeNumber(), "BLOB", Blob.class),
    MEDIUM_BLOB(JDBCType.BLOB.getVendorTypeNumber(), "MEDIUMBLOB", Blob.class),
    LONG_BLOB(JDBCType.BLOB.getVendorTypeNumber(), "LONGBLOB", Blob.class),

    DATE(JDBCType.DATE.getVendorTypeNumber(), "DATE", Date.class),
    TIME(JDBCType.TIME.getVendorTypeNumber(), "TIME", Time.class),
    DATETIME(JDBCType.DATE.getVendorTypeNumber(), "DATETIME", Date.class),
    TIMESTAMP(JDBCType.TIMESTAMP.getVendorTypeNumber(), "TIMESTAMP", Timestamp.class),

    OBJECT(JDBCType.JAVA_OBJECT.getVendorTypeNumber(), "UNKNOWN", Object.class),
    ;

    public static final FieldType[] VALUES = FieldType.values();

    public static FieldType fromAttachment(@NonNull Class<?> attachment) {
        for (FieldType fieldType : VALUES) {

            if (fieldType.cls.isAssignableFrom(attachment)) {
                return fieldType;
            }
        }

        return OBJECT;
    }

    int id;

    String sql;
    Class<?> cls;
}