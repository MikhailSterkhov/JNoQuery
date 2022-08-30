package com.itzstonlex.jnq.orm.data.repository.annotation;

import com.itzstonlex.jnq.content.field.FieldOperator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface EntityParam {

    FieldOperator operator() default FieldOperator.EQUAL;

    String name() default "";
}
