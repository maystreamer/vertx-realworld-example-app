package com.greyseal.vertx.boot.helper;

import com.greyseal.vertx.boot.Constant.Configuration;
import com.greyseal.vertx.boot.config.VertxBootConfig;
import io.vertx.core.json.JsonObject;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class ConfigHelper {

    private static final JsonObject CONFIG = VertxBootConfig.INSTANCE.getConfig();

    public static JsonObject getConfig() {
        return CONFIG;
    }

    public static JsonObject loadConfigurationByEnvironment() {
        final String environment = System.getenv(Configuration.ENVIRONMENT);
        if (isBlank(environment)) {
            System.out.println("Missing environment variable. Example set ENV=dev");
            throw new RuntimeException("Missing environment");
        }
        return CONFIG.getJsonObject(environment);
    }

    public static JsonObject getValueByEnvironment(final String key) {
        return loadConfigurationByEnvironment().getJsonObject(key);
    }

    public static String getVerticlePackage() {
        return CONFIG.getString(Configuration.VERTICLE_PACKAGE_NAME);
    }

    public static String getHandlerPackage() {
        return CONFIG.getString(Configuration.HANDLER_PACKAGE_NAME);
    }

    public static String getProtocol() {
        return CONFIG.getString(Configuration.HTTP_PROTOCOL, "http://");
    }

    public static String getHost() {
        return CONFIG.getString(Configuration.HTTP_SERVER_HOST, "localhost");
    }

    public static int getPort() {
        return CONFIG.getInteger(Configuration.HTTP_SERVER_PORT, 8080);
    }

    public static String getContextPath() {
        return CONFIG.getString(Configuration.CONTEXT_PATH);
    }

    public static String getServerContextPath() {
        return new StringBuilder(getProtocol()).append(getHost()).append(":").append(getPort()).append(getContextPath()).toString();
    }

    public static boolean isHTTP2Enabled() {
        return CONFIG.getBoolean(Configuration.IS_HTTP2_ENABLED, false);
    }

    public static JsonObject getHTTPServerOptions() {
        return CONFIG.getJsonObject(Configuration.HTTP_SERVER_OPTIONS, new JsonObject());
    }

    public static boolean isSSLEnabled() {
        return CONFIG.getBoolean(Configuration.IS_SSL_ENABLED, false);
    }

    public static JsonObject getMongoConfig() {
        return getValueByEnvironment(Configuration.MONGO_CONFIG);
    }
}