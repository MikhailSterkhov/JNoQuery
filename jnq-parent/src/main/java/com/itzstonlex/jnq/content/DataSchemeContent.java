package com.itzstonlex.jnq.content;

import com.itzstonlex.jnq.DataConnection;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DataSchemeContent {

    DataConnection connection;

    Map<String, DataTableContent> tables;

    public Set<String> getTablesNames() {
        return tables == null ? Collections.emptySet() : tables.values().stream().map(DataTableContent::getName).collect(Collectors.toSet());
    }

    public void create(@NonNull DataTableContent table) {
        connection.
    }

    public void drop(@NonNull DataTableContent table) {

    }

}
