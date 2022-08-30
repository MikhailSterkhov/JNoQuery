package com.itzstonlex.jnq.orm.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MappingLastUpdateTime {

    String PROPERTY_KEY_NAME = "@last_update_time";

    TimeUnit unit();
}
