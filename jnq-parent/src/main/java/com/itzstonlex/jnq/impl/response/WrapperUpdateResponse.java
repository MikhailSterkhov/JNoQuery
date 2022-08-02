package com.itzstonlex.jnq.impl.response;

import com.itzstonlex.jnq.response.UpdateResponse;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WrapperUpdateResponse implements UpdateResponse {

    boolean supportsGeneratedKey;

    @Getter
    int generatedKey, affectedRows;

    @Override
    public boolean supportsGeneratedKey() {
        return supportsGeneratedKey;
    }

}
