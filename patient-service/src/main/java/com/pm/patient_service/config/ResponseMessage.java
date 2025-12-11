package com.pm.patient_service.config;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface ResponseMessage {
    String value() default "";
}
