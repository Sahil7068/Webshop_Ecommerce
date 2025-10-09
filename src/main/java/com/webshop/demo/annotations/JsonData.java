package com.webshop.demo.annotations;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface JsonData {
    String file();
    String key() default "";
    Class<?> targetClass() default Object.class;
}

