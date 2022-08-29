package com.itzstonlex.jnq.orm.request;

import com.itzstonlex.jnq.orm.request.type.MappingRequestSearch;
import com.itzstonlex.jnq.orm.request.type.MappingRequestSaving;
import lombok.NonNull;

public interface MappingRequestFactory {

    @NonNull
    MappingRequestSearch beginSearch();

    @NonNull
    MappingRequestSaving beginSaving();
}
