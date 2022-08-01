package com.itzstonlex.jnq.request.option;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.sql.ResultSet;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum RequestType {

    FORWARD_ONLY(ResultSet.TYPE_FORWARD_ONLY), // 1003

    SCROLL_INSENSITIVE(ResultSet.TYPE_SCROLL_INSENSITIVE), // 1004

    SCROLL_SENSITIVE(ResultSet.TYPE_SCROLL_SENSITIVE); // 1005

    int index;
}
