package com.itzstonlex.jnq;

import com.itzstonlex.jnq.response.DataResponseRow;
import lombok.NonNull;

import java.util.Map;

public interface DataMapProvider {

    Map<String, Object> provide(@NonNull DataResponseRow responseRow) throws Exception;

    void write(@NonNull DataResponseRow responseRow, @NonNull Map<String, Object> map) throws Exception;
}
