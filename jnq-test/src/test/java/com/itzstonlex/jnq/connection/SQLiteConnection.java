package com.itzstonlex.jnq.connection;

import com.itzstonlex.jnq.JnqException;
import com.itzstonlex.jnq.jdbc.JDBCConnection;
import com.itzstonlex.jnq.jdbc.JDBCHelper;
import lombok.NonNull;

import java.io.File;

public class SQLiteConnection extends JDBCConnection {

    public SQLiteConnection(@NonNull File dbFile) throws JnqException {
        super(JDBCHelper.toSqlite(dbFile));
    }
}
