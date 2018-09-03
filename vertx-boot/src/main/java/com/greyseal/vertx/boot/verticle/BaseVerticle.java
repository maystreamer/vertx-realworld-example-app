package com.greyseal.vertx.boot.verticle;

import com.greyseal.vertx.boot.Constant.Configuration;
import com.greyseal.vertx.boot.config.VertxBootConfig;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.reactivex.core.AbstractVerticle;

public abstract class BaseVerticle extends AbstractVerticle {
    public static String CONTEXT_PATH = VertxBootConfig.INSTANCE.getConfig().getString(Configuration.CONTEXT_PATH);
    protected static Logger logger = LoggerFactory.getLogger(BaseVerticle.class);

}
