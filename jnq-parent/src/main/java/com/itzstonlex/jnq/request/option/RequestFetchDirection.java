package com.itzstonlex.jnq.request.option;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.sql.ResultSet;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum RequestFetchDirection {

    FORWARD(ResultSet.FETCH_FORWARD), // 1000

    REVERSE(ResultSet.FETCH_REVERSE), // 1001

    UNKNOWN(ResultSet.FETCH_UNKNOWN); // 1002

    int index;
}
