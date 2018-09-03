package io.greyseal.realworld.service;

import io.vertx.codegen.annotations.*;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.FindOptions;
import io.vertx.ext.mongo.MongoClientUpdateResult;
import io.vertx.ext.mongo.UpdateOptions;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.ext.mongo.MongoClient;
import io.vertx.serviceproxy.ServiceProxyBuilder;

import java.util.List;

@VertxGen
@ProxyGen
public interface DatabaseService {
    public static final String DEFAULT_ADDRESS = DatabaseService.class.getName();

    /**
     * Method called to create a proxy (to consume the service).
     *
     * @param vertx   vert.x
     * @param address the address on the event bus where the service is served.
     * @return the proxy
     */
    @GenIgnore
    static DatabaseService createProxy(final Vertx vertx, final String address) {
        return new ServiceProxyBuilder(vertx.getDelegate()).setAddress(address).build(DatabaseService.class);
    }

    @GenIgnore
    static DatabaseService create(final MongoClient mongoClient, final Handler<AsyncResult<DatabaseService>> readyHandler) {
        return new DatabaseServiceImpl(mongoClient, readyHandler);
    }

    @Fluent
    public DatabaseService findOne(final String collection, final JsonObject query, final JsonObject fields, final Handler<AsyncResult<JsonObject>> resultHandler);

    @Fluent
    public DatabaseService find(final String collection, final JsonObject query, final FindOptions options, Handler<AsyncResult<List<JsonObject>>> resultHandler);

    @Fluent
    public DatabaseService insertOne(final String collection, final JsonObject document, final Handler<AsyncResult<String>> resultHandler);

    @Fluent
    public DatabaseService upsert(final String collection, final JsonObject data, final JsonObject query, final UpdateOptions options, Handler<AsyncResult<MongoClientUpdateResult>> resultHandler);

    @Fluent
    public DatabaseService findOneAndUpdate(final String collection, final JsonObject query, final JsonObject toUpdate, final FindOptions findOptions, final UpdateOptions updateOptions, final Handler<AsyncResult<JsonObject>> resultHandler);

    @ProxyClose
    public void close();
}
