package com.itzstonlex.jnq.exception;

public class JnqResponseException extends Exception {

    public JnqResponseException() {
        super();
    }

    public JnqResponseException(String message) {
        super(message);
    }

    public JnqResponseException(String message, Object... formatReplacements) {
        this(String.format(message, formatReplacements));
    }

    public JnqResponseException(Throwable throwable) {
        super(throwable);
    }

    public JnqResponseException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
