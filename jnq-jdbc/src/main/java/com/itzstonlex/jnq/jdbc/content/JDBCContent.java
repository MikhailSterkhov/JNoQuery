package com.itzstonlex.jnq.jdbc.content;

import com.itzstonlex.jnq.content.JnqContent;

import java.sql.Connection;

public interface JDBCContent extends JnqContent {

    Connection getJdbcConnection();
}
