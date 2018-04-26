package org.zechac.atlas.dynamic;


import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target(METHOD)
@Retention(RUNTIME)
public @interface ServiceDescription {

    String name() default "";

    String url() default "";

    InvokeType invokeType() default InvokeType.HTTP_ALL;

    String params() default "";

    Class returnType() default void.class;

    ServiceType serviceType() default ServiceType.NORMAL;

}
