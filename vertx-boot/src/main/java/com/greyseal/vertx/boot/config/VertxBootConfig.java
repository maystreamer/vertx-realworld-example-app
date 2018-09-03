package com.greyseal.vertx.boot.config;

import io.vertx.core.json.JsonObject;

public enum VertxBootConfig {
    INSTANCE;
    private JsonObject config;

    public JsonObject getConfig() {
        return config;
    }

    public void setConfig(final JsonObject config) {
        this.config = config;
    }
}