package com.itzstonlex.jnq.impl.orm.request.type;

import com.itzstonlex.jnq.DataConnection;
import com.itzstonlex.jnq.impl.field.MappingDataField;
import com.itzstonlex.jnq.impl.orm.request.AbstractMappingRequest;
import com.itzstonlex.jnq.impl.orm.request.MappingRequestExecutorImpl;
import com.itzstonlex.jnq.orm.ObjectMappingService;
import com.itzstonlex.jnq.orm.request.MappingRequestExecutor;
import com.itzstonlex.jnq.orm.request.type.MappingRequestUpdate;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MappingRequestUpdateImpl extends AbstractMappingRequest implements MappingRequestUpdate<MappingDataField> {

    public MappingRequestUpdateImpl(@NonNull DataConnection connection, @NonNull String schema, String table, @NonNull ObjectMappingService<MappingDataField> objectMappingService) {
        super(connection, schema, table, objectMappingService);
    }

    @Override
    public @NonNull MappingRequestExecutor compile() {
        return new MappingRequestExecutorImpl(getConnection(), getSchema(), getTable(), getMapper(), null);
    }
}
