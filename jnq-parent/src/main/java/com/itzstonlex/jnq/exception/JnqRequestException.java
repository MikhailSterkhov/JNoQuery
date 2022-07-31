package com.itzstonlex.jnq.exception;

public class JnqRequestException extends Exception {

    public JnqRequestException() {
        super();
    }

    public JnqRequestException(String message) {
        super(message);
    }

    public JnqRequestException(String message, Object... formatReplacements) {
        this(String.format(message, formatReplacements));
    }

    public JnqRequestException(Throwable throwable) {
        super(throwable);
    }

    public JnqRequestException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
