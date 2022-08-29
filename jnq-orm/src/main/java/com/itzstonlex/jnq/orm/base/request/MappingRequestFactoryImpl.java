package com.itzstonlex.jnq.orm.base.request;

import com.itzstonlex.jnq.content.type.SchemaContent;
import com.itzstonlex.jnq.orm.data.ObjectMappingService;
import com.itzstonlex.jnq.orm.base.request.type.MappingRequestSearchImpl;
import com.itzstonlex.jnq.orm.base.request.type.MappingRequestSavingImpl;
import com.itzstonlex.jnq.orm.request.MappingRequestFactory;
import com.itzstonlex.jnq.orm.request.type.MappingRequestSearch;
import com.itzstonlex.jnq.orm.request.type.MappingRequestSaving;
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
    public MappingRequestSearch beginSearch() {
        return new MappingRequestSearchImpl(schemaContent, table, objectMappingService);
    }

    @Override
    public MappingRequestSaving beginSaving() {
        return new MappingRequestSavingImpl(schemaContent, table, objectMappingService);
    }
}
