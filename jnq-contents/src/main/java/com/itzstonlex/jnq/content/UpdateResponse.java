package com.itzstonlex.jnq.content;

public interface UpdateResponse {

    boolean supportsGeneratedKey();

    int getGeneratedKey();

    int getAffectedRows();
}
