package com.itzstonlex.jnq;

public interface JnqFactory<T> {

    T create() throws JnqException;
}
