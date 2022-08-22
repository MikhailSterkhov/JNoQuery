package com.itzstonlex.jnq.jdbc;

import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.io.File;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;

@UtilityClass
public class JDBCHelper {

    public final int MYSQL_PORT = 3306;
    public final int CLICKHOUSE_PORT = 8123;
    public final int POSTGRESQL_PORT = 5432;

    public final String H2_DRIVER_NAME = "h2";
    public final String MYSQL_DRIVER_NAME = "mysql";
    public final String CLICKHOUSE_DRIVER_NAME = "clickhouse";
    public final String POSTGRESQL_DRIVER_NAME = "postgresql";

    public final String H2_DRIVER_CLASS = "org.h2.Driver";
    public final String MYSQL_DRIVER_CLASS = "com.mysql.jdbc.Driver";
    public final String CLICKHOUSE_DRIVER_CLASS = "com.clickhouse.jdbc.ClickHouseDriver";

    public final String H2_DEFAULT_SCHEMA_NAME = "PUBLIC";
    public final String MYSQL_DEFAULT_SCHEMA_NAME = "mysql";
    public final String CLICKHOUSE_DEFAULT_SCHEMA_NAME = "default";

    public final String JDBC_URL_FORMAT = "jdbc:%s://%s:%s/";
    public final String SQLITE_URL_PREFIX = "jdbc:sqlite:";
    public final String JDBC_MEM_FORMAT = "jdbc:%s:mem:";

    public @NonNull String makeLink(@NonNull String driverName, @NonNull String host, int port) {
        return String.format(JDBC_URL_FORMAT, driverName.toLowerCase(), host, port);
    }

    public @NonNull String toMysql(@NonNull String host, int port) {
        return makeLink(MYSQL_DRIVER_NAME, host, port);
    }

    public @NonNull String toMysql(@NonNull String host) {
        return toMysql(host, MYSQL_PORT);
    }

    public @NonNull String toClickHouse(@NonNull String host, int port) {
        return makeLink(CLICKHOUSE_DRIVER_NAME, host, port);
    }

    public @NonNull String toClickHouse(@NonNull String host) {
        return toClickHouse(host, CLICKHOUSE_PORT);
    }

    public @NonNull String toPostgresql(@NonNull String host, int port) {
        return makeLink(POSTGRESQL_DRIVER_NAME, host, port);
    }

    public @NonNull String toPostgresql(@NonNull String host) {
        return toPostgresql(host, POSTGRESQL_PORT);
    }

    public @NonNull String toH2() {
        return String.format(JDBC_MEM_FORMAT, H2_DRIVER_NAME);
    }

    public @NonNull String toSqlite(@NonNull File dbFile) {
        return SQLITE_URL_PREFIX + dbFile.getAbsolutePath();
    }

    public @NonNull String toSqlite(@NonNull Path dbPath) {
        return toSqlite(dbPath.toFile());
    }

    @SneakyThrows
    public Connection getConnection(@NonNull String url, @NonNull String username, @NonNull String password) {
        return DriverManager.getConnection(url, username, password);
    }

}
