package com.itzstonlex.jnq.sql.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class SQLUtility {

    public final int MYSQL_PORT = 3306;
    public final int CLICKHOUSE_PORT = 8123;

    public final String MYSQL_DRIVER_NAME = "mysql";
    public final String CLICKHOUSE_DRIVER_NAME = "clickhouse";

    public final String MYSQL_DRIVER_CLASS = "com.mysql.jdbc.Driver";
    public final String CLICKHOUSE_DRIVER_CLASS = "com.clickhouse.jdbc.ClickHouseDriver";

    public final String JDBC_URL_FORMAT = "jdbc:%s://%s:%s/%s";

    public String toJDBC(String driverName, String scheme, String host, int port) {
        return String.format(JDBC_URL_FORMAT, driverName.toLowerCase(), host, port, scheme);
    }

    public String toMysqlJDBC(String scheme, String host, int port) {
        return toJDBC(MYSQL_DRIVER_NAME, scheme, host, port);
    }

    public String toMysqlJDBC(String host, int port) {
        return toMysqlJDBC("", host, port);
    }

    public String toMysqlJDBC(String host) {
        return toMysqlJDBC(host, MYSQL_PORT);
    }

    public String toClickHouseJDBC(String scheme, String host, int port) {
        return toJDBC(CLICKHOUSE_DRIVER_NAME, scheme, host, port);
    }

    public String toClickHouseJDBC(String host, int port) {
        return toClickHouseJDBC("", host, port);
    }

    public String toClickHouseJDBC(String host) {
        return toClickHouseJDBC(host, CLICKHOUSE_PORT);
    }

}
