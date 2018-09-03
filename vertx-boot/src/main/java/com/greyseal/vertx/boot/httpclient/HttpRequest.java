package com.greyseal.vertx.boot.httpclient;

import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
import java.util.Map;

public class HttpRequest {
    private HttpMethod httpMethod;
    private String resourceURL;
    private JsonObject payload;
    private Map<String, String> headers;

    public HttpRequest(HttpMethod httpMethod, String resourceURL, JsonObject payload, Map<String, String> headers) {
        this.httpMethod = httpMethod;
        this.resourceURL = resourceURL;
        this.payload = payload;
        this.headers = headers;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getResourceURL() {
        return resourceURL;
    }

    public void setResourceURL(String resourceURL) {
        this.resourceURL = resourceURL;
    }

    public JsonObject getPayload() {
        return payload;
    }

    public void setPayload(JsonObject payload) {
        this.payload = payload;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }
}
