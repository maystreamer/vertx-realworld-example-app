package com.greyseal.vertx.boot.httpclient;

import com.greyseal.vertx.boot.helper.ConfigHelper;
import io.vertx.core.json.JsonObject;

import java.util.Objects;

public abstract class AbstractHttpClient implements IHttpClient {
    protected static final JsonObject CONFIGURATION = ConfigHelper.loadConfigurationByEnvironment();

    public static JsonObject getHttpClientOptions() {
        final JsonObject httpClientOptions = CONFIGURATION.getJsonObject("http_client_options");
        Objects.requireNonNull(httpClientOptions, "HTTP Client Options is required");
        return httpClientOptions;
    }
}