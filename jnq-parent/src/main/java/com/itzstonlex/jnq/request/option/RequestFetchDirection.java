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

    FETCH_FORWARD(ResultSet.FETCH_FORWARD),

    FETCH_REVERSE(ResultSet.FETCH_REVERSE),

    FETCH_UNKNOWN(ResultSet.FETCH_UNKNOWN);

    int index;
}
