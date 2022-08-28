package com.itzstonlex.jnq.connection;

import com.itzstonlex.jnq.JnqException;
import com.itzstonlex.jnq.jdbc.JDBCConnection;
import com.itzstonlex.jnq.jdbc.JDBCHelper;
import lombok.NonNull;

public class H2Connection extends JDBCConnection {

    public H2Connection(@NonNull String username, @NonNull String password) throws JnqException {
        super(JDBCHelper.toH2(), username, password);
    }
}
