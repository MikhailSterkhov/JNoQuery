package com.itzstonlex.jnq;

import com.itzstonlex.jnq.content.DataContent;
import com.itzstonlex.jnq.impl.content.SchemaContent;
import com.itzstonlex.jnq.impl.content.TableContent;
import com.itzstonlex.jnq.impl.field.MappingDataField;
import com.itzstonlex.jnq.orm.ObjectMappingService;
import com.itzstonlex.jnq.request.Request;
import lombok.NonNull;

import java.util.Set;

public interface DataConnection {

    void updateContents();

    SchemaContent getSchema(@NonNull String name);

    TableContent getTable(@NonNull String schema, @NonNull String name);

    TableContent getTable(@NonNull DataContent schema, @NonNull String name);

    @NonNull
    Set<SchemaContent> getActiveSchemas();

    @NonNull
    Set<TableContent> getActiveTables(@NonNull String schema);

    @NonNull
    Request createRequest(@NonNull DataContent executableContent);

    @NonNull
    ObjectMappingService<MappingDataField> getObjectMappings();
}
