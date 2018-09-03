package com.greyseal.vertx.boot.httpclient;

import io.reactivex.Single;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.reactivex.core.MultiMap;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.core.buffer.Buffer;
import io.vertx.reactivex.ext.web.client.HttpResponse;
import io.vertx.reactivex.ext.web.client.WebClient;

import java.util.Map;

public class VertxWebClient extends AbstractHttpClient {
    private static final VertxWebClient INSTANCE = new VertxWebClient();
    private static WebClient webClient;

    private VertxWebClient() {
    }

    public static IHttpClient create() {
        return INSTANCE;
    }

    public static void create(final Vertx vertx) {
        webClient = WebClient.create(vertx, buildWebClientOptions());
    }

    private static WebClientOptions buildWebClientOptions() {
        return new WebClientOptions(getHttpClientOptions());
    }

    @Override
    public Single<HttpClientResponse> doExecute(final HttpRequest httpRequest) {
        final io.vertx.reactivex.ext.web.client.HttpRequest<Buffer> request = webClient.requestAbs(httpRequest.getHttpMethod(), httpRequest.getResourceURL());
        final JsonObject _payload = httpRequest.getPayload();
        final Map<String, String> _headers = httpRequest.getHeaders();
        if (null != _headers) {
            request.headers().addAll(getHeaders(_headers));
        }
        if (null != _payload) {
            final Buffer buffer = Buffer.buffer(_payload.toString());
            request.headers().add(HttpHeaders.CONTENT_LENGTH.toString(), buffer.length() + "");
            return request.rxSendBuffer(buffer).flatMap(this::wrapResponse);
        } else {
            return request.rxSend().flatMap(this::wrapResponse);
        }
    }

    private MultiMap getHeaders(final Map<String, String> headers) {
        final MultiMap _headers = MultiMap.caseInsensitiveMultiMap();
        headers.forEach((k, v) -> {
            _headers.add(k, v);
        });
        return _headers;
    }

    private Single<HttpClientResponse> wrapResponse(final HttpResponse<Buffer> response) {
        return toBody(response)
                .doOnSuccess(this::trace)
                .map(buffer -> new HttpClientResponse(buffer, response.headers(), response.statusCode())
                );
    }

    private Single<Buffer> toBody(final HttpResponse<Buffer> response) {
        final Buffer buffer = response.body();
        if (null != buffer) {
            return Single.just(buffer);
        } else {
            System.out.println("Empty body");
            return Single.just(Buffer.buffer());
        }
    }

    private void trace(final Buffer result) {
        System.out.println(result.toString());
        //TODO: log trace here
    }
}
