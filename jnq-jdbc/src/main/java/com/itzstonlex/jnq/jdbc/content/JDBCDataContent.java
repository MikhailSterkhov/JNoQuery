package com.itzstonlex.jnq.jdbc.content;

import com.itzstonlex.jnq.content.DataContent;

import java.sql.Connection;

public interface JDBCDataContent extends DataContent {

    Connection getJdbcConnection();
}
