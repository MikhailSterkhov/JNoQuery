package com.itzstonlex.jnq.content.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.sql.ResultSet;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum RequestHoldability {

    HOLD_CURSORS_OVER_COMMIT(ResultSet.HOLD_CURSORS_OVER_COMMIT), // 1

    CLOSE_CURSORS_AT_COMMIT(ResultSet.CLOSE_CURSORS_AT_COMMIT); // 2

    int index;
}
