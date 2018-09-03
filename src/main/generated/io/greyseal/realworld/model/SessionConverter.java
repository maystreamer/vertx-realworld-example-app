/*
 * Copyright (c) 2014 Red Hat, Inc. and others
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

package io.greyseal.realworld.model;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;

/**
 * Converter for {@link io.greyseal.realworld.model.Session}.
 *
 * NOTE: This class has been automatically generated from the {@link io.greyseal.realworld.model.Session} original class using Vert.x codegen.
 */
public class SessionConverter {

  public static void fromJson(JsonObject json, Session obj) {
    if (json.getValue("isActive") instanceof Boolean) {
      obj.setIsActive((Boolean)json.getValue("isActive"));
    }
    if (json.getValue("token") instanceof String) {
      obj.setToken((String)json.getValue("token"));
    }
  }

  public static void toJson(Session obj, JsonObject json) {
    if (obj.getIsActive() != null) {
      json.put("isActive", obj.getIsActive());
    }
    if (obj.getToken() != null) {
      json.put("token", obj.getToken());
    }
  }
}