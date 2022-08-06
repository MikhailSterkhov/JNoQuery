package com.itzstonlex.jnq.connection;

import com.itzstonlex.jnq.exception.JnqException;
import com.itzstonlex.jnq.sql.SQLConnection;
import com.itzstonlex.jnq.sql.SQLHelper;
import lombok.NonNull;

public class H2Connection extends SQLConnection {

    private static final String MYSQL_MODE_NAME = "MySQL";

    public H2Connection(@NonNull String username, @NonNull String password) throws JnqException {
        super(SQLHelper.toH2JDBC(), username, password);

        setMode(MYSQL_MODE_NAME); // <- this line is not required.
    }
}
