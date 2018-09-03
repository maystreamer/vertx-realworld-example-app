package com.greyseal.vertx.boot.handler;

import com.greyseal.vertx.boot.Constant.Configuration;
import com.greyseal.vertx.boot.util.DateUtil;
import com.greyseal.vertx.boot.util.ResponseUtil;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.ext.web.RoutingContext;
import java.time.Instant;

public class PostHandler extends BaseHandler {

    public PostHandler(Vertx vertx) {
        super(vertx);
    }

    public static PostHandler create(final Vertx vertx) {
        return new PostHandler(vertx);
    }

    @Override
    public void handle(RoutingContext event) {
        System.out.println("PostHandler called");
        final String method = ResponseUtil.getCookieValue(event, Configuration.COOKIE_METHOD);
        final String traceId = ResponseUtil.getHeaderValue(event, Configuration.TRACE_ID);
        final long totalTimeTaken = DateUtil.dateDiff(Instant.now(), Long.parseLong(ResponseUtil.getCookieValue(event, Configuration.COOKIE_DATE)));
        LOGGER.info(String.join(" ", "TraceID [", traceId, "] : Finished executing method ", method, "and took", totalTimeTaken + "", "MS"));
        event.response().setChunked(true);
        event.response().end(event.getBodyAsString());
    }
}