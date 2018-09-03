package io.greyseal.realworld.util;

import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;

import java.util.Map;

public class JsonUtil {

	public static <T> String encode(T object) {
		return Json.encode(object);
	}

	@SuppressWarnings("unchecked")
	public static <T> JsonObject decode(T object) {
		return new JsonObject((Map<String, Object>) Json.mapper.convertValue(object, Map.class));
	}

	public static <T> T decode(JsonObject jsonObject, Class<T> clazz) {
		return decode(jsonObject.toString(), clazz);
	}

	public static <T> T decode(String json, Class<T> clazz) {
		return Json.decodeValue(json, clazz);
	}
}