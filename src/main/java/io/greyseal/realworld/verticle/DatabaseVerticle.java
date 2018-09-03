package io.greyseal.realworld.verticle;

import com.greyseal.vertx.boot.Constant.VerticleType;
import com.greyseal.vertx.boot.annotation.Verticle;
import com.greyseal.vertx.boot.helper.ConfigHelper;
import com.greyseal.vertx.boot.verticle.BaseVerticle;
import io.greyseal.realworld.service.DatabaseService;
import io.vertx.core.Future;
import io.vertx.reactivex.ext.mongo.MongoClient;
import io.vertx.serviceproxy.ServiceBinder;

@Verticle(type = VerticleType.WORKER, configuration = "database_verticle")
public class DatabaseVerticle extends BaseVerticle {
    public static DatabaseService dbService;
    public static io.greyseal.realworld.service.reactivex.DatabaseService rxDBService;

    @Override
    public void start(Future<Void> startFuture) {
        try {
            super.start();
            final MongoClient mongoClient = MongoClient.createShared(vertx, ConfigHelper.getMongoConfig());
            dbService = DatabaseService.create(mongoClient, ready -> {
                if (ready.succeeded()) {
                    new ServiceBinder(vertx.getDelegate()).setAddress(DatabaseService.DEFAULT_ADDRESS)
                            .register(DatabaseService.class, ready.result()).completionHandler(ar -> {
                        if (ar.succeeded()) {
                            startFuture.complete();
                            //DatabaseServiceHelper.INSTANCE.setDbService(DatabaseService.createProxy(vertx, DatabaseService.DEFAULT_ADDRESS));
                            rxDBService = new io.greyseal.realworld.service.reactivex.DatabaseService(dbService);
                        } else {
                            startFuture.fail(ar.cause());
                        }
                    });
                } else {
                    startFuture.fail(ready.cause());
                }
            });
        } catch (Exception ex) {
            logger.error("Error while deploying DatabaseVerticle ", ex);
            startFuture.fail(ex);
        }
    }

    @Override
    public void stop() throws Exception {
        dbService.close();
    }
}
