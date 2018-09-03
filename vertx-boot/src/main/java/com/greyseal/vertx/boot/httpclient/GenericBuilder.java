package com.greyseal.vertx.boot.httpclient;

import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

public class GenericBuilder<T> {
    private T instance;
    private boolean ifCond = true; // default

    public GenericBuilder(Class<T> clazz) {
        try {
            instance = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static <T> GenericBuilder<T> build(Class<T> clazz) {
        return new GenericBuilder<>(clazz);
    }

    public GenericBuilder<T> with(Consumer<T> setter) {
        if (ifCond) {
            setter.accept(instance);
        }
        return this;
    }

    public T get() {
        return instance;
    }

    public GenericBuilder<T> If(BooleanSupplier condition) {
        this.ifCond = condition.getAsBoolean();
        return this;
    }

    public GenericBuilder<T> endIf() {
        this.ifCond = true;
        return this;
    }
}