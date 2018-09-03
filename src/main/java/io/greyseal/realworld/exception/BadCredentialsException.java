package io.greyseal.realworld.exception;

import com.greyseal.vertx.boot.exception.RestException;

public class BadCredentialsException extends RestException {

    private static final long serialVersionUID = -8578395250030949877L;
    private static final String DEFAULT_MESSAGE = "Authentication Failed. Username or Password not valid";
    private static final int DEFAULT_STATUS_CODE = 401;

    public BadCredentialsException() {
        super(DEFAULT_MESSAGE);
        statusCode = DEFAULT_STATUS_CODE;
    }

    /**
     * Constructs a <code>BadCredentialsException</code> with the specified message.
     *
     * @param msg the detail message
     */
    public BadCredentialsException(String msg) {
        super(msg != null ? msg : DEFAULT_MESSAGE);
        statusCode = DEFAULT_STATUS_CODE;
    }

    /**
     * Constructs a <code>BadCredentialsException</code> with the specified message
     * and root cause.
     *
     * @param msg the detail message
     * @param t   root cause
     */
    public BadCredentialsException(String msg, Throwable t) {
        super(msg != null ? msg : DEFAULT_MESSAGE, t);
        statusCode = DEFAULT_STATUS_CODE;
    }

    public BadCredentialsException(String msg, int code) {
        super(msg != null ? msg : DEFAULT_MESSAGE, code);
    }
}