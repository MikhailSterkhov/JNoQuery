package com.itzstonlex.jnq.connection;

import com.itzstonlex.jnq.exception.JnqException;
import com.itzstonlex.jnq.jdbc.JDBCConnection;
import com.itzstonlex.jnq.jdbc.JDBCHelper;
import lombok.NonNull;

public class H2Connection extends JDBCConnection {

    private static final String MYSQL_MODE_NAME = "MySQL";

    public H2Connection(@NonNull String username, @NonNull String password) throws JnqException {
        super(JDBCHelper.toH2(), username, password);

        setMode(MYSQL_MODE_NAME); // <- this line is not required.
    }
}
