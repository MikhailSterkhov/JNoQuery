package com.itzstonlex.jnq;

import com.itzstonlex.jnq.orm.exception.JnqObjectMappingException;

public class JnqException extends JnqObjectMappingException {

    public JnqException() {
        super();
    }

    public JnqException(String message) {
        super(message);
    }

    public JnqException(String message, Object... formatReplacements) {
        this(String.format(message, formatReplacements));
    }

    public JnqException(Throwable throwable) {
        super(throwable);
    }

    public JnqException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
