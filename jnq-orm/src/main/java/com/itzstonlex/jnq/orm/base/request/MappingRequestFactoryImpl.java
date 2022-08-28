package com.itzstonlex.jnq.orm.base.request;

import com.itzstonlex.jnq.content.type.SchemaContent;
import com.itzstonlex.jnq.orm.data.ObjectMappingService;
import com.itzstonlex.jnq.orm.base.request.type.MappingRequestFinderImpl;
import com.itzstonlex.jnq.orm.base.request.type.MappingRequestUpdateImpl;
import com.itzstonlex.jnq.orm.request.MappingRequestFactory;
import com.itzstonlex.jnq.orm.request.type.MappingRequestFinder;
import com.itzstonlex.jnq.orm.request.type.MappingRequestUpdate;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MappingRequestFactoryImpl implements MappingRequestFactory {

    SchemaContent schemaContent;
    String table;

    ObjectMappingService objectMappingService;

    @Override
    public MappingRequestFinder newFinder() {
        return new MappingRequestFinderImpl(schemaContent, table, objectMappingService);
    }

    @Override
    public MappingRequestUpdate newUpdate() {
        return new MappingRequestUpdateImpl(schemaContent, table, objectMappingService);
    }
}
