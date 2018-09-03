package com.greyseal.vertx.boot.exception;

import com.greyseal.vertx.boot.Constant.ErrorMessage;

public class InvalidTokenException extends RestException {

    /**
     *
     */
    private static final long serialVersionUID = 2023354109026635110L;
    private static final String DEFAULT_MESSAGE = ErrorMessage.INVALID_TOKEN_EXCEPTION;
    private static final int DEFAULT_STATUS_CODE = 401;

    public InvalidTokenException() {
        super(DEFAULT_MESSAGE);
        statusCode = DEFAULT_STATUS_CODE;
    }

    /**
     * Constructs a <code>InvalidTokenException</code> with the specified message.
     *
     * @param msg the detail message
     */
    public InvalidTokenException(String msg) {
        super(msg != null ? msg : DEFAULT_MESSAGE);
        statusCode = DEFAULT_STATUS_CODE;
    }

    /**
     * Constructs a <code>InvalidTokenException</code> with the specified message
     * and root cause.
     *
     * @param msg the detail message
     * @param t   root cause
     */
    public InvalidTokenException(String msg, Throwable t) {
        super(msg != null ? msg : DEFAULT_MESSAGE, t);
        statusCode = DEFAULT_STATUS_CODE;
    }

    /**
     * Constructs a <code>InvalidTokenException</code> with the specified message
     * and root cause.
     *
     * @param msg  the detail message
     * @param code http status code
     */
    public InvalidTokenException(String msg, int code) {
        super(msg != null ? msg : DEFAULT_MESSAGE, code);
    }
}