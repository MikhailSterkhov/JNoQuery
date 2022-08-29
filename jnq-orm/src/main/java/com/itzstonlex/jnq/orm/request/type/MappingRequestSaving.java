package com.itzstonlex.jnq.orm.request.type;

import com.itzstonlex.jnq.orm.request.MappingRequest;
import lombok.NonNull;

public interface MappingRequestSaving extends MappingRequest {

    @NonNull
    MappingRequestSaving checkAvailability();
}
