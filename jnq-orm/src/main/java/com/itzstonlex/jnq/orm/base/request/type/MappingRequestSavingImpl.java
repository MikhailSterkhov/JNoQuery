package com.itzstonlex.jnq.orm.base.request.type;

import com.itzstonlex.jnq.content.type.SchemaContent;
import com.itzstonlex.jnq.orm.data.ObjectMappingService;
import com.itzstonlex.jnq.orm.base.request.AbstractMappingRequest;
import com.itzstonlex.jnq.orm.base.ObjectMappingRepositoryImpl;
import com.itzstonlex.jnq.orm.ObjectMappingRepository;
import com.itzstonlex.jnq.orm.request.type.MappingRequestSaving;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MappingRequestSavingImpl extends AbstractMappingRequest implements MappingRequestSaving {

    boolean checkAvailability;

    public MappingRequestSavingImpl(@NonNull SchemaContent schema, @NonNull String table, @NonNull ObjectMappingService objectMappingService) {
        super(schema, table, objectMappingService);
    }

    @Override
    public @NonNull MappingRequestSaving checkAvailability() {
        this.checkAvailability = true;
        return this;
    }

    @Override
    public @NonNull ObjectMappingRepository compile() {
        return new ObjectMappingRepositoryImpl(getSchema(), getTable(), getMapper(), getObjectMappingService(), null, this);
    }
}
