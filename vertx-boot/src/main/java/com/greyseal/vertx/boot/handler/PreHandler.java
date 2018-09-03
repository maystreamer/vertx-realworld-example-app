package com.greyseal.vertx.boot.handler;

import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.ext.web.RoutingContext;

public class PreHandler extends BaseHandler {

    public PreHandler(Vertx vertx) {
        super(vertx);
    }

    public static PreHandler create(final Vertx vertx) {
        return new PreHandler(vertx);
    }

    @Override
    public void handle(RoutingContext event) {
        //can be used as AOP
        System.out.println("PreHandler called");
        event.next();
    }
}
