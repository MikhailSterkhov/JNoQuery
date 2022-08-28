package com.itzstonlex.jnq.content;

import com.itzstonlex.jnq.content.UpdateResponse;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UpdateResponseDecorator implements UpdateResponse {

    boolean supportsGeneratedKey;

    @Getter
    int generatedKey, affectedRows;

    @Override
    public boolean supportsGeneratedKey() {
        return supportsGeneratedKey;
    }

}
