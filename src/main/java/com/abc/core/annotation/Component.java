package com.abc.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
public @interface Component {
    public String value() default "";
    public String description() default "no description";
}
