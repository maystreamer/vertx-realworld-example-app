package com.greyseal.vertx.boot.httpclient;

import io.reactivex.Single;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
import java.util.Map;
import java.util.function.Consumer;

public class HttpRequestProcessor {
    protected static final IHttpClient<Single<HttpClientResponse>> HTTP_CLIENT = IHttpClient.getInstance(VertxWebClient::create);
    public HttpMethod httpMethod;
    public String resourceURL;
    public JsonObject payload;
    public Map<String, String> headers;
    private HttpRequest httpRequest;

    public HttpRequestProcessor with(
            Consumer<HttpRequestProcessor> builderFunction) {
        builderFunction.accept(this);
        return this;
    }

    public HttpRequestProcessor buildRequest() {
        this.httpRequest = new HttpRequest(httpMethod, resourceURL, payload, headers);
        return this;
    }

    public Single<HttpClientResponse> doExecute() {
        return HTTP_CLIENT.doExecute(httpRequest);
    }
}