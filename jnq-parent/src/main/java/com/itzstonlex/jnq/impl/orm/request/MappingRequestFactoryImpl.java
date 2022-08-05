package com.itzstonlex.jnq.impl.orm.request;

import com.itzstonlex.jnq.DataConnection;
import com.itzstonlex.jnq.content.DataContent;
import com.itzstonlex.jnq.impl.field.MappingDataField;
import com.itzstonlex.jnq.impl.orm.request.type.MappingRequestFinderImpl;
import com.itzstonlex.jnq.impl.orm.request.type.MappingRequestUpdateImpl;
import com.itzstonlex.jnq.orm.ObjectMappingService;
import com.itzstonlex.jnq.orm.request.MappingRequestFactory;
import com.itzstonlex.jnq.orm.request.type.MappingRequestFinder;
import com.itzstonlex.jnq.orm.request.type.MappingRequestUpdate;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MappingRequestFactoryImpl implements MappingRequestFactory<MappingDataField> {

    DataConnection connection;
    String schema, table;

    ObjectMappingService<MappingDataField> objectMappingService;

    @Override
    public MappingRequestFinder<MappingDataField> newFinder() {
        return new MappingRequestFinderImpl(connection, schema, table, objectMappingService);
    }

    @Override
    public MappingRequestUpdate newUpdate() {
        return new MappingRequestUpdateImpl(connection, schema, table, objectMappingService);
    }
}
