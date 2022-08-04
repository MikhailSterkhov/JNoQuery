package com.itzstonlex.jnq.impl.orm.request.type;

import com.itzstonlex.jnq.DataConnection;
import com.itzstonlex.jnq.impl.field.MappingDataField;
import com.itzstonlex.jnq.impl.orm.request.AbstractMappingRequest;
import com.itzstonlex.jnq.impl.orm.request.MappingRequestExecutorImpl;
import com.itzstonlex.jnq.orm.ObjectMappingService;
import com.itzstonlex.jnq.orm.request.MappingRequestExecutor;
import com.itzstonlex.jnq.orm.request.type.MappingRequestFinder;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

import java.util.ArrayList;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MappingRequestFinderImpl extends AbstractMappingRequest implements MappingRequestFinder<MappingDataField> {

    List<MappingDataField> includes;

    @NonFinal
    int limit;

    public MappingRequestFinderImpl(@NonNull DataConnection connection, @NonNull String schema, @NonNull String table, @NonNull ObjectMappingService<MappingDataField> objectMappingService) {
        super(connection, schema, table, objectMappingService);
        this.includes = new ArrayList<>();
    }

    @Override
    public int limit() {
        return limit;
    }

    @Override
    public @NonNull List<MappingDataField> includes() {
        return includes;
    }

    @Override
    public @NonNull MappingRequestFinder<MappingDataField> withLimit(int limit) {
        this.limit = limit;
        return this;
    }

    @Override
    public @NonNull MappingRequestFinder<MappingDataField> withInclude(@NonNull MappingDataField field) {
        if (!includes.contains(field)) {
            includes.add(field);
        }

        return this;
    }

    @Override
    public @NonNull MappingRequestExecutor compile() {
        return new MappingRequestExecutorImpl(getConnection(), getSchema(), getTable(), getMapper(), this);
    }
}
