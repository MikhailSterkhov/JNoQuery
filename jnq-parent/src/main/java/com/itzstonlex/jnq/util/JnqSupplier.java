package com.itzstonlex.jnq.util;

import com.itzstonlex.jnq.exception.JnqException;

public interface JnqSupplier<T> {

    T get() throws JnqException;
}
