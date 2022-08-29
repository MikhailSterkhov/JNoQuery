package com.itzstonlex.jnq;

public class JnqException extends RuntimeException {

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
