package com.greyseal.vertx.boot.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import io.vertx.core.http.HttpMethod;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMapping {
	String name() default "";

	String path() default "";

	HttpMethod method() default HttpMethod.GET;

	String[] consumes() default {};

	String[] produces() default {};
}