package com.greyseal.vertx.boot.verticle;

import com.greyseal.vertx.boot.annotation.Verticle;
import com.greyseal.vertx.boot.helper.ConfigHelper;
import io.vertx.core.Context;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.reactivex.core.CompositeFuture;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

public class MainVerticle extends AbstractVerticle {
    private static final String BASE_VERTICLE_PACKAGE = "com.greyseal.vertx.boot.verticle";
    private static final String VERTICLE_PACKAGE = ConfigHelper.getVerticlePackage();
    protected static Logger logger = LoggerFactory.getLogger(MainVerticle.class);
    static Reflections reflections;

    static {
        if (null == VERTICLE_PACKAGE) {
            System.out.println("No verticles to configure. Only default HttpServerVerticle will be deployed.");
        }
        reflections = new Reflections(new ConfigurationBuilder().addUrls(ClasspathHelper.forPackage(VERTICLE_PACKAGE)).addUrls(ClasspathHelper.forPackage(BASE_VERTICLE_PACKAGE))
                .setScanners(new SubTypesScanner(false), new MethodAnnotationsScanner())
                .filterInputsBy(new FilterBuilder().includePackage(VERTICLE_PACKAGE, BASE_VERTICLE_PACKAGE)));
    }

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        //reflections.getTypesAnnotatedWith(Verticle.class);
        final Set<Class<? extends BaseVerticle>> clazzes = reflections.getSubTypesOf(BaseVerticle.class);
        List<io.vertx.reactivex.core.Future> futures = new ArrayList<io.vertx.reactivex.core.Future>();
        if (null != clazzes && !clazzes.isEmpty()) {
            for (Class<?> verticle : clazzes) {
                futures.add(deploy(verticle));
            }
            CompositeFuture.all(futures).setHandler(ar -> {
                if (ar.succeeded()) {
                    logger.info("All verticles deployed successfully");
                    System.out.println("All verticles deployed successfully");
                    startFuture.complete();
                } else {
                    logger.error("An error occured while deploying verticles ", ar.cause());
                    ar.cause().printStackTrace();
                    startFuture.fail(ar.cause());
                }

            });
        } else {
            startFuture.fail("No verticles to deploy");
        }
    }

    @Override
    public void init(Vertx vertx, Context context) {
        super.init(vertx, context);
    }

    public io.vertx.reactivex.core.Future<Void> deploy(final Class<?> clazz) {
        io.vertx.reactivex.core.Future<Void> future = io.vertx.reactivex.core.Future.future();
        final String clazzName = clazz.getCanonicalName();
        Verticle verticle = clazz.getAnnotation(Verticle.class);
        vertx.deployVerticle(clazzName, getDeploymentOptions(verticle.configuration()), ar -> {
            if (ar.succeeded()) {
                logger.info(String.join(" ", clazzName, "deployed successfully with deployment id: ", ar.result()));
                future.complete();
            } else {
                logger.error(String.join(" ", "Failed to deploy verticle ", clazzName));
                future.fail(ar.cause());
            }
        });
        return future;
    }

    public DeploymentOptions getDeploymentOptions(final String configName) {
        try {
            final String key = configName;
            final JsonObject json = ConfigHelper.getValueByEnvironment(key);
            return new DeploymentOptions(json);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to load DeploymentOptions from configuration file for verticle: " + configName);
        }
    }
}
