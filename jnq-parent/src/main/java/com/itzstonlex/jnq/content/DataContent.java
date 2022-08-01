package com.itzstonlex.jnq.content;

import com.itzstonlex.jnq.exception.JnqException;
import lombok.NonNull;

import java.util.concurrent.CompletableFuture;

public interface DataContent {

    @NonNull
    String getName();

    @NonNull
    CompletableFuture<Void> clear() throws JnqException;

    @NonNull
    CompletableFuture<Void> drop() throws JnqException;
}
