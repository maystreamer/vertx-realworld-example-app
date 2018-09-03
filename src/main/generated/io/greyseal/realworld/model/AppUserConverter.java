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
 * Converter for {@link io.greyseal.realworld.model.AppUser}.
 *
 * NOTE: This class has been automatically generated from the {@link io.greyseal.realworld.model.AppUser} original class using Vert.x codegen.
 */
public class AppUserConverter {

  public static void fromJson(JsonObject json, AppUser obj) {
    if (json.getValue("bio") instanceof String) {
      obj.setBio((String)json.getValue("bio"));
    }
    if (json.getValue("email") instanceof String) {
      obj.setEmail((String)json.getValue("email"));
    }
    if (json.getValue("image") instanceof String) {
      obj.setImage((String)json.getValue("image"));
    }
    if (json.getValue("isActive") instanceof Boolean) {
      obj.setIsActive((Boolean)json.getValue("isActive"));
    }
    if (json.getValue("password") instanceof String) {
      obj.setPassword((String)json.getValue("password"));
    }
    if (json.getValue("salt") instanceof String) {
      obj.setSalt((String)json.getValue("salt"));
    }
    if (json.getValue("userName") instanceof String) {
      obj.setUserName((String)json.getValue("userName"));
    }
  }

  public static void toJson(AppUser obj, JsonObject json) {
    if (obj.getBio() != null) {
      json.put("bio", obj.getBio());
    }
    if (obj.getEmail() != null) {
      json.put("email", obj.getEmail());
    }
    if (obj.getImage() != null) {
      json.put("image", obj.getImage());
    }
    if (obj.getIsActive() != null) {
      json.put("isActive", obj.getIsActive());
    }
    if (obj.getPassword() != null) {
      json.put("password", obj.getPassword());
    }
    if (obj.getSalt() != null) {
      json.put("salt", obj.getSalt());
    }
    if (obj.getUserName() != null) {
      json.put("userName", obj.getUserName());
    }
  }
}