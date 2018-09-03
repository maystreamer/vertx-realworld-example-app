/*
 * Copyright 2014 Red Hat, Inc.
 *
 * Red Hat licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package io.greyseal.realworld.service.reactivex;

import java.util.Map;
import io.reactivex.Observable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.Completable;
import io.reactivex.Maybe;
import java.util.List;
import io.vertx.ext.mongo.FindOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.ext.mongo.MongoClientUpdateResult;
import io.vertx.ext.mongo.UpdateOptions;


@io.vertx.lang.reactivex.RxGen(io.greyseal.realworld.service.DatabaseService.class)
public class DatabaseService {

  @Override
  public String toString() {
    return delegate.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    DatabaseService that = (DatabaseService) o;
    return delegate.equals(that.delegate);
  }
  
  @Override
  public int hashCode() {
    return delegate.hashCode();
  }

  public static final io.vertx.lang.reactivex.TypeArg<DatabaseService> __TYPE_ARG = new io.vertx.lang.reactivex.TypeArg<>(
    obj -> new DatabaseService((io.greyseal.realworld.service.DatabaseService) obj),
    DatabaseService::getDelegate
  );

  private final io.greyseal.realworld.service.DatabaseService delegate;
  
  public DatabaseService(io.greyseal.realworld.service.DatabaseService delegate) {
    this.delegate = delegate;
  }

  public io.greyseal.realworld.service.DatabaseService getDelegate() {
    return delegate;
  }

  public DatabaseService findOne(String collection, JsonObject query, JsonObject fields, Handler<AsyncResult<JsonObject>> resultHandler) { 
    delegate.findOne(collection, query, fields, resultHandler);
    return this;
  }

  public Single<JsonObject> rxFindOne(String collection, JsonObject query, JsonObject fields) { 
    return new io.vertx.reactivex.core.impl.AsyncResultSingle<JsonObject>(handler -> {
      findOne(collection, query, fields, handler);
    });
  }

  public DatabaseService find(String collection, JsonObject query, FindOptions options, Handler<AsyncResult<List<JsonObject>>> resultHandler) { 
    delegate.find(collection, query, options, resultHandler);
    return this;
  }

  public Single<List<JsonObject>> rxFind(String collection, JsonObject query, FindOptions options) { 
    return new io.vertx.reactivex.core.impl.AsyncResultSingle<List<JsonObject>>(handler -> {
      find(collection, query, options, handler);
    });
  }

  public DatabaseService insertOne(String collection, JsonObject document, Handler<AsyncResult<String>> resultHandler) { 
    delegate.insertOne(collection, document, resultHandler);
    return this;
  }

  public Single<String> rxInsertOne(String collection, JsonObject document) { 
    return new io.vertx.reactivex.core.impl.AsyncResultSingle<String>(handler -> {
      insertOne(collection, document, handler);
    });
  }

  public DatabaseService upsert(String collection, JsonObject data, JsonObject query, UpdateOptions options, Handler<AsyncResult<MongoClientUpdateResult>> resultHandler) { 
    delegate.upsert(collection, data, query, options, resultHandler);
    return this;
  }

  public Single<MongoClientUpdateResult> rxUpsert(String collection, JsonObject data, JsonObject query, UpdateOptions options) { 
    return new io.vertx.reactivex.core.impl.AsyncResultSingle<MongoClientUpdateResult>(handler -> {
      upsert(collection, data, query, options, handler);
    });
  }

  public DatabaseService findOneAndUpdate(String collection, JsonObject query, JsonObject toUpdate, FindOptions findOptions, UpdateOptions updateOptions, Handler<AsyncResult<JsonObject>> resultHandler) { 
    delegate.findOneAndUpdate(collection, query, toUpdate, findOptions, updateOptions, resultHandler);
    return this;
  }

  public Single<JsonObject> rxFindOneAndUpdate(String collection, JsonObject query, JsonObject toUpdate, FindOptions findOptions, UpdateOptions updateOptions) { 
    return new io.vertx.reactivex.core.impl.AsyncResultSingle<JsonObject>(handler -> {
      findOneAndUpdate(collection, query, toUpdate, findOptions, updateOptions, handler);
    });
  }

  public void close() { 
    delegate.close();
  }


  public static  DatabaseService newInstance(io.greyseal.realworld.service.DatabaseService arg) {
    return arg != null ? new DatabaseService(arg) : null;
  }
}
