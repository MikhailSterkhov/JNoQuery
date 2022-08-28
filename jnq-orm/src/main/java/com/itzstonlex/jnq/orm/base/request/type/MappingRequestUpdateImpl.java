package com.itzstonlex.jnq.orm.base.request.type;

import com.itzstonlex.jnq.content.type.SchemaContent;
import com.itzstonlex.jnq.orm.data.ObjectMappingService;
import com.itzstonlex.jnq.orm.base.request.AbstractMappingRequest;
import com.itzstonlex.jnq.orm.base.request.MappingRequestExecutorImpl;
import com.itzstonlex.jnq.orm.request.MappingRequestExecutor;
import com.itzstonlex.jnq.orm.request.type.MappingRequestUpdate;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MappingRequestUpdateImpl extends AbstractMappingRequest implements MappingRequestUpdate {

    public MappingRequestUpdateImpl(@NonNull SchemaContent schema, @NonNull String table, @NonNull ObjectMappingService objectMappingService) {
        super(schema, table, objectMappingService);
    }

    @Override
    public @NonNull MappingRequestExecutor compile() {
        return new MappingRequestExecutorImpl(getSchema(), getTable(), getMapper(), getObjectMappingService(), null);
    }
}
