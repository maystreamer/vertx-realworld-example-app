package io.greyseal.realworld.auth;

import com.greyseal.vertx.boot.auth.AbstractSession;
import io.vertx.core.json.JsonObject;

public class AppSession extends AbstractSession {

    @Override
    public AppSession getSession() {
        return null;
    }

    @Override
    public String getSessionToken() {
        return null;
    }

    @Override
    public JsonObject toJson() {
        return null;
    }
}
