package com.greyseal.vertx.boot.annotation;

import com.greyseal.vertx.boot.Constant.VerticleType;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Verticle {
	VerticleType type() default VerticleType.STANDARD;
	String configuration() default "";
}