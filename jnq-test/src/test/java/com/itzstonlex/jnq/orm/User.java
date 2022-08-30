package com.itzstonlex.jnq.orm;

import com.itzstonlex.jnq.orm.annotation.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.concurrent.TimeUnit;

@MappingEntity // annotation from JNQ
@Getter
@ToString
public class User {

    @Setter
    @MappingPrimary // annotation from JNQ
    @MappingID // annotation from JNQ
    private int id;

    @MappingColumn // annotation from JNQ
    private String name;

    @MappingColumn("register_date") // annotation from JNQ
    private long registerTimeMillis;

    @MappingColumn("last_update_date") // annotation from JNQ
    @MappingLastUpdateTime(unit = TimeUnit.MILLISECONDS) // annotation from JNQ
    private long lastUpdateTimeMillis;

    // required for new instance calling.
    private User() {
    }

    // You object realisations.
    public User(String name, long registerTimeMillis) {
        this.name = name;
        this.registerTimeMillis = registerTimeMillis;
    }

    @MappingInitMethod // annotation from JNQ
    private void init() {
        System.out.println("User `" + name + "` was initialized!");
    }
}