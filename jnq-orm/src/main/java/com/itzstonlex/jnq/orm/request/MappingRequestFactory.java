package com.itzstonlex.jnq.orm.request;

import com.itzstonlex.jnq.orm.request.type.MappingRequestFinder;
import com.itzstonlex.jnq.orm.request.type.MappingRequestUpdate;

public interface MappingRequestFactory {

    MappingRequestFinder newFinder();

    MappingRequestUpdate newUpdate();
}
