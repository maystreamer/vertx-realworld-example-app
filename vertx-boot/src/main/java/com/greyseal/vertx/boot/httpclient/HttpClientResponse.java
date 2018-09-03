package com.greyseal.vertx.boot.httpclient;

import io.vertx.reactivex.core.MultiMap;
import io.vertx.reactivex.core.buffer.Buffer;

public class HttpClientResponse {
    private Buffer body;
    private MultiMap headers;
    private int statusCode;

    public HttpClientResponse(final Buffer body, final MultiMap headers, final int statusCode) {
        this.body = body;
        this.headers = headers;
        this.statusCode = statusCode;
    }

    public Buffer getBody() {
        return body;
    }

    public void setBody(Buffer body) {
        this.body = body;
    }

    public MultiMap getHeaders() {
        return headers;
    }

    public void setHeaders(MultiMap headers) {
        this.headers = headers;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
