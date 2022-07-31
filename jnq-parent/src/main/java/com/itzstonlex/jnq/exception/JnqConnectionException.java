package com.itzstonlex.jnq.exception;

public class JnqConnectionException extends Exception {

    public JnqConnectionException() {
        super();
    }

    public JnqConnectionException(String message) {
        super(message);
    }

    public JnqConnectionException(String message, Object... formatReplacements) {
        this(String.format(message, formatReplacements));
    }

    public JnqConnectionException(Throwable throwable) {
        super(throwable);
    }

    public JnqConnectionException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
