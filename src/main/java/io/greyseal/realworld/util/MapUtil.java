package io.greyseal.realworld.util;

import java.util.Map;

public class MapUtil {

	public static boolean isNullOrEmpty(final Map<?, ?> m) {
		return m == null || m.isEmpty();
	}
}
