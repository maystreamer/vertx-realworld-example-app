package com.greyseal.vertx.boot.helper;

import com.greyseal.vertx.boot.auth.SessionUser;
import com.greyseal.vertx.boot.exception.InvalidTokenException;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.AuthProvider;
import io.vertx.ext.auth.User;

public class SessionHelper implements AuthProvider {
    protected static String AUTH_SCHEME = "BEARER";

    protected String parseSessionToken(final String authHeader) {
        String[] parts = authHeader.split(" ");
        String sscheme = parts[0];
        if (AUTH_SCHEME.equals(sscheme)) {
            throw new InvalidTokenException("Invalid token scheme. Should be BEARER");
        }
        if (parts.length < 2) {
            throw new InvalidTokenException();
        }
        return parts[1];
    }

    @Override
    public void authenticate(JsonObject authInfo, Handler<AsyncResult<User>> resultHandler) {

    }

    public void validate(JsonObject authInfo, Handler<AsyncResult<User>> resultHandler) {

    }

    public void validate(String authInfo, Handler<AsyncResult<User>> resultHandler) {
        resultHandler.handle(io.vertx.core.Future.succeededFuture(new SessionUser(null, null)));
    }
}
