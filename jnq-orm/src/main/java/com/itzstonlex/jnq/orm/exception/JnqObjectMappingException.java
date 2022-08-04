package com.itzstonlex.jnq.orm.exception;

public class JnqObjectMappingException extends Exception {

    public JnqObjectMappingException() {
        super();
    }

    public JnqObjectMappingException(String message) {
        super(message);
    }

    public JnqObjectMappingException(String message, Object... formatReplacements) {
        this(String.format(message, formatReplacements));
    }

    public JnqObjectMappingException(Throwable throwable) {
        super(throwable);
    }

    public JnqObjectMappingException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
