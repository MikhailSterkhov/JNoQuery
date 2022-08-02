package com.itzstonlex.jnq.sql;

import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.sql.Connection;
import java.sql.DriverManager;

@UtilityClass
public class SQLHelper {

    public final int MYSQL_PORT = 3306;
    public final int CLICKHOUSE_PORT = 8123;

    public final String MYSQL_DRIVER_NAME = "mysql";
    public final String CLICKHOUSE_DRIVER_NAME = "clickhouse";

    public final String MYSQL_DRIVER_CLASS = "com.mysql.jdbc.Driver";
    public final String CLICKHOUSE_DRIVER_CLASS = "com.clickhouse.jdbc.ClickHouseDriver";

    public final String JDBC_URL_FORMAT = "jdbc:%s://%s:%s/%s";

    public @NonNull String toJDBC(@NonNull String driverName, @NonNull String schema, @NonNull String host, int port) {
        return String.format(JDBC_URL_FORMAT, driverName.toLowerCase(), host, port, schema);
    }

    public @NonNull String toMysqlJDBC(@NonNull String schema, @NonNull String host, int port) {
        return toJDBC(MYSQL_DRIVER_NAME, schema, host, port);
    }

    public @NonNull String toMysqlJDBC(@NonNull String host, int port) {
        return toMysqlJDBC("", host, port);
    }

    public @NonNull String toMysqlJDBC(@NonNull String host) {
        return toMysqlJDBC(host, MYSQL_PORT);
    }

    public @NonNull String toClickHouseJDBC(@NonNull String schema, @NonNull String host, int port) {
        return toJDBC(CLICKHOUSE_DRIVER_NAME, schema, host, port);
    }

    public @NonNull String toClickHouseJDBC(@NonNull String host, int port) {
        return toClickHouseJDBC("", host, port);
    }

    public @NonNull String toClickHouseJDBC(@NonNull String host) {
        return toClickHouseJDBC(host, CLICKHOUSE_PORT);
    }

    @SneakyThrows
    public Connection getConnection(@NonNull String url, @NonNull String username, @NonNull String password) {
        return DriverManager.getConnection(url, username, password);
    }

}
