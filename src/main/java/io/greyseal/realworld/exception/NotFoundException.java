package io.greyseal.realworld.exception;

import io.vertx.core.json.JsonObject;
import io.vertx.serviceproxy.ServiceException;

public class NotFoundException extends ServiceException {

    private static final long serialVersionUID = 1191196550974778913L;
    private static final String DEFAULT_MESSAGE = "Entry Not found";
    private static final int DEFAULT_STATUS_CODE = 404;

    public NotFoundException(int failureCode, String message, JsonObject debugInfo) {
        super(failureCode, message, debugInfo);
    }

    public NotFoundException(int failureCode, String message) {
        super(failureCode, message);
    }

    public NotFoundException() {
        super(DEFAULT_STATUS_CODE, DEFAULT_MESSAGE);
    }
}