package com.itzstonlex.jnq.content.exception;

public class JnqContentException extends RuntimeException {

    public JnqContentException() {
        super();
    }

    public JnqContentException(String message) {
        super(message);
    }

    public JnqContentException(String message, Object... formatReplacements) {
        this(String.format(message, formatReplacements));
    }

    public JnqContentException(Throwable throwable) {
        super(throwable);
    }

    public JnqContentException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
