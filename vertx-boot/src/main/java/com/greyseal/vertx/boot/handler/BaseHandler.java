package com.greyseal.vertx.boot.handler;

import io.vertx.core.Handler;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.ext.web.RoutingContext;


public abstract class BaseHandler implements Handler<RoutingContext> {
	protected static Logger LOGGER = LoggerFactory.getLogger(BaseHandler.class);
	protected Vertx vertx;

	public BaseHandler(final Vertx vertx) {
		this.vertx = vertx;
	}
}