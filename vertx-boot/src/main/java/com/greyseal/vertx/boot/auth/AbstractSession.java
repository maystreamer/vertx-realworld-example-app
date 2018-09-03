package com.greyseal.vertx.boot.auth;

import io.vertx.core.json.JsonObject;

public abstract class AbstractSession {

    public abstract AbstractSession getSession();

    public abstract String getSessionToken();

    public abstract JsonObject toJson();
}
