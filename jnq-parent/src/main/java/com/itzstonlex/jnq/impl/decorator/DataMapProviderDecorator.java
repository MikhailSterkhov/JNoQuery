package com.itzstonlex.jnq.impl.decorator;

import com.itzstonlex.jnq.DataMapProvider;
import com.itzstonlex.jnq.response.DataResponseRow;
import lombok.NonNull;

import java.util.Map;

public class DataMapProviderDecorator implements DataMapProvider {

    @Override
    public Map<String, Object> provide(@NonNull DataResponseRow responseRow) {
        return null;
    }

    @Override
    public void write(@NonNull DataResponseRow responseRow, @NonNull Map<String, Object> map) {

    }
}
