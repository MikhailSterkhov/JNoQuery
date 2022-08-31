package com.itzstonlex.jnq.connection;

import com.itzstonlex.jnq.JnqException;
import com.itzstonlex.jnq.jdbc.JDBCConnection;
import com.itzstonlex.jnq.jdbc.JDBCHelper;
import lombok.NonNull;

public class OracleSQLConnection extends JDBCConnection {

    public OracleSQLConnection(@NonNull String username, @NonNull String password) throws JnqException {
        super(JDBCHelper.toOracleSQL("localhost"), username, password);
    }
}
