package com.greyseal.vertx.boot.auth;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.AuthProvider;
import io.vertx.ext.auth.User;

public class SessionUser implements User {

    protected final AbstractSession session;
    protected AuthProvider authProvider;

    public SessionUser(final AbstractSession session, final AuthProvider authProvider) {
        this.session = session;
        setAuthProvider(authProvider);
    }

    @Override
    public User isAuthorized(String authority, Handler<AsyncResult<Boolean>> resultHandler) {
        return null;
    }

    @Override
    public User clearCache() {
        return null;
    }

    @Override
    public JsonObject principal() {
        return session.toJson();
    }

    public AuthProvider getAuthProvider() {
        return this.authProvider;
    }

    @Override
    public void setAuthProvider(AuthProvider authProvider) {
        this.authProvider = authProvider;
    }

    public AbstractSession getCurrentSession() {
        return this.session;
    }
}
