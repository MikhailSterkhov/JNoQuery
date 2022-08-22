package com.itzstonlex.jnq.util;

import com.itzstonlex.jnq.exception.JnqException;

public interface JnqFactory<T> {

    T get() throws JnqException;
}
