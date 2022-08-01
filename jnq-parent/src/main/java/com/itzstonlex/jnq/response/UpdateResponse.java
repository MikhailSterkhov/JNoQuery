package com.itzstonlex.jnq.response;

public interface UpdateResponse {

    boolean supportsGeneratedKey();

    int getGeneratedKey();

    int getAffectedRows();
}
