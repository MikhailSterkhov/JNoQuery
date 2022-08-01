package com.itzstonlex.jnq;

import com.itzstonlex.jnq.response.ResponseLine;
import lombok.NonNull;

import java.util.Map;

public interface DataMapProvider {

    Map<String, Object> provide(@NonNull ResponseLine responseRow) throws Exception;

    void write(@NonNull ResponseLine responseRow, @NonNull Map<String, Object> map) throws Exception;
}
