package io.greyseal.realworld.service;

import com.greyseal.vertx.boot.exception.RestException;
import com.mongodb.MongoWriteException;
import io.greyseal.realworld.exception.DuplicateEntityException;
import io.reactivex.Single;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.FindOptions;
import io.vertx.ext.mongo.MongoClientUpdateResult;
import io.vertx.ext.mongo.UpdateOptions;
import io.vertx.reactivex.ext.mongo.MongoClient;

import java.util.List;
import java.util.Optional;

public class DatabaseServiceImpl implements DatabaseService {

    private MongoClient client;

    public DatabaseServiceImpl(final MongoClient mongoClient, final Handler<AsyncResult<DatabaseService>> readyHandler) {
        this.client = mongoClient;
        this.client.rxGetCollections().subscribe(resp -> {
            readyHandler.handle(Future.succeededFuture(this));
        }, cause -> {
            readyHandler.handle(Future.failedFuture(cause));
        });
    }

    @Override
    public DatabaseService findOne(final String collection, final JsonObject query, final JsonObject fields, final Handler<AsyncResult<JsonObject>> resultHandler) {
        try {
            client.rxFindOne(collection, query, fields).subscribe(resp -> {
                resultHandler.handle(Future.succeededFuture(resp));
            }, cause -> {
                resultHandler.handle(Future.failedFuture(cause));
            });
        } catch (Exception ex) {
            resultHandler.handle(Future.failedFuture(ex));
        }
        return this;
    }

    @Override
    public DatabaseService find(final String collection, final JsonObject query, final FindOptions options, Handler<AsyncResult<List<JsonObject>>> resultHandler) {
        try {
            client.rxFindWithOptions(collection, query, options).subscribe(resp -> {
                resultHandler.handle(Future.succeededFuture(resp));
            }, cause -> {
                resultHandler.handle(Future.failedFuture(cause));
            });
        } catch (Exception ex) {
            resultHandler.handle(Future.failedFuture(ex));
        }
        return this;
    }

    public Single<Optional<JsonObject>> rxFindOne(final String collection, final JsonObject query, final JsonObject fields) {
        try {
            return client.rxFindOne(collection, query, fields).map(result -> {
                return Optional.of(result);
            });
        } catch (Exception ex) {
            throw new RestException("Error while executing rxFindOne: ", ex);
        }
    }

    @Override
    public DatabaseService insertOne(final String collection, final JsonObject document, final Handler<AsyncResult<String>> resultHandler) {
        try {
            client.rxInsert(collection, document).subscribe(resp -> {
                resultHandler.handle(Future.succeededFuture(resp));
            }, cause -> {
                final MongoWriteException mwx = (MongoWriteException) cause;
                if (mwx.getCode() == 11000) {
                    resultHandler.handle(Future.failedFuture(new DuplicateEntityException()));
                } else {
                    resultHandler.handle(Future.failedFuture(cause));
                }
            });
        } catch (Exception ex) {
            resultHandler.handle(Future.failedFuture(ex));
        }
        return this;
    }

    @Override

    public DatabaseService upsert(final String collection, final JsonObject query, final JsonObject toUpdate, final UpdateOptions options, Handler<AsyncResult<MongoClientUpdateResult>> resultHandler) {
        try {
            client.rxUpdateCollectionWithOptions(collection, query, toUpdate, options).subscribe(resp -> {
                resultHandler.handle(Future.succeededFuture(resp));
            }, cause -> {
                resultHandler.handle(Future.failedFuture(cause));
            });
        } catch (Exception ex) {
            resultHandler.handle(Future.failedFuture(ex));
        }
        return this;
    }

    @Override
    public DatabaseService findOneAndUpdate(final String collection, final JsonObject query, final JsonObject toUpdate, final FindOptions findOptions, final UpdateOptions updateOptions, final Handler<AsyncResult<JsonObject>> resultHandler) {
        try {
            client.rxFindOneAndUpdateWithOptions(collection, query, toUpdate, findOptions, updateOptions).subscribe(resp -> {
                resultHandler.handle(Future.succeededFuture(resp));
            }, cause -> {
                resultHandler.handle(Future.failedFuture(cause));
            });
        } catch (Exception ex) {
            resultHandler.handle(Future.failedFuture(ex));
        }
        return this;
    }

    @Override
    public void close() {
        client.close();
    }
}