package com.greyseal.vertx.boot.httpclient;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.core.MultiMap;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.core.buffer.Buffer;
import io.vertx.reactivex.core.http.HttpClient;
import io.vertx.reactivex.core.http.HttpClientRequest;
import io.vertx.reactivex.core.http.HttpClientResponse;

import java.util.Map;

//https://sourceforge.net/p/garyproject00/wiki/BaseHttpMicroServicesVerticle/

public class VertxHttpClient extends AbstractHttpClient {

    private static final VertxHttpClient INSTANCE = new VertxHttpClient();

    private static HttpClient httpClient;

    private VertxHttpClient() {
    }

    public static IHttpClient create() {
        return INSTANCE;
    }

    public static void create(final Vertx vertx) {
        httpClient = vertx.createHttpClient(buildHttpClientOptions());
    }

    private static HttpClientOptions buildHttpClientOptions() {
        return new HttpClientOptions(getHttpClientOptions());
    }

    @Override
    public Flowable<HttpClientResponse> doExecute(final HttpRequest httpRequest) {
        HttpClientRequest request = httpClient.requestAbs(httpRequest.getHttpMethod(), httpRequest.getResourceURL().toString());
        final JsonObject _payload = httpRequest.getPayload();
        if (null != _payload) {
            Buffer buffer = Buffer.buffer(_payload.toString());
            request.headers().add(HttpHeaders.CONTENT_LENGTH.toString(), buffer.length() + "");
            request.write(buffer);
        }
        final Map<String, String> _headers = httpRequest.getHeaders();
        if (null != _headers) {
            request.headers().addAll(getHeaders(_headers));
        }
        return request
                .toFlowable()
                .onBackpressureDrop()
                .doOnSubscribe(subscription -> request.end());
    }

    private MultiMap getHeaders(final Map<String, String> headers) {
        final MultiMap _headers = MultiMap.caseInsensitiveMultiMap();
        headers.forEach((k, v) -> {
            _headers.add(k, v);
        });
        return _headers;
    }

//    static <T> void createInstanceAndCallMethod(
//            Supplier<T> instanceSupplier, Consumer<T> methodCaller) {
//        T o = instanceSupplier.get();
//        methodCaller.accept(o);
//    }
}
