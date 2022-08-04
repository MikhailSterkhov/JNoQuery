package com.itzstonlex.jnq.orm.request.type;

import com.itzstonlex.jnq.orm.request.MappingRequest;
import lombok.NonNull;

import java.util.List;

public interface MappingRequestFinder<Include> extends MappingRequest {

    int limit();

    @NonNull
    List<Include> includes();

    @NonNull
    MappingRequestFinder<Include> withLimit(int limit);

    @NonNull
    MappingRequestFinder<Include> withInclude(@NonNull Include include);
}
