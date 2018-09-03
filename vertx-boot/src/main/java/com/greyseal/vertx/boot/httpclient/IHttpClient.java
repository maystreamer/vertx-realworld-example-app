package com.greyseal.vertx.boot.httpclient;

import java.util.function.Supplier;

public interface IHttpClient<T> {
    public static IHttpClient getInstance(final Supplier<IHttpClient> t) {
        return t.get();
    }

    public T doExecute(final HttpRequest httpRequest);
}