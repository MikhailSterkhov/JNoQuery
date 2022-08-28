package com.itzstonlex.jnq.content.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.sql.ResultSet;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum RequestConcurrency {

    READ_ONLY(ResultSet.CONCUR_READ_ONLY), // 1007

    UPDATABLE(ResultSet.CONCUR_UPDATABLE); // 1008

    int index;
}
