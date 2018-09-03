package com.greyseal.vertx.boot.handler;

import com.greyseal.vertx.boot.exception.InvalidTokenException;
import com.greyseal.vertx.boot.helper.SessionHelper;
import io.vertx.core.http.HttpHeaders;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.ext.web.RoutingContext;

public class AuthHandler extends BaseHandler {
    public AuthHandler(Vertx vertx) {
        super(vertx);
    }

    public static AuthHandler create(final Vertx vertx) {
        return new AuthHandler(vertx);
    }

    @Override
    public void handle(RoutingContext event) {
        System.out.println("AuthHandler called");
        final String authHeader = event.request().headers().get(HttpHeaders.AUTHORIZATION.toString());
        final SessionHelper helper = new SessionHelper();
        helper.validate(authHeader, handler -> {
            if (!handler.failed()) {
                event.getDelegate().setUser(handler.result());
                event.next();
            } else {
                event.fail(new InvalidTokenException());
            }
        });
    }
}