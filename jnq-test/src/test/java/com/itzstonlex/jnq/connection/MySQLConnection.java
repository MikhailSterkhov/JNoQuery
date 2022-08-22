package com.itzstonlex.jnq.connection;

import com.itzstonlex.jnq.exception.JnqException;
import com.itzstonlex.jnq.jdbc.JDBCConnection;
import com.itzstonlex.jnq.jdbc.JDBCHelper;
import lombok.NonNull;

public class MySQLConnection extends JDBCConnection {

    public MySQLConnection(@NonNull String username, @NonNull String password) throws JnqException {
        super(JDBCHelper.MYSQL_DRIVER_CLASS, JDBCHelper.toMysql("localhost", 3306), username, password);
    }
}
