package io.greyseal.realworld.exception;

import com.greyseal.vertx.boot.exception.RestException;

public class UserNotActivatedException extends RestException {

    private static final long serialVersionUID = -8865837805346850171L;
    private static final String DEFAULT_MESSAGE = "Authentication Failed. User is not activated yet";
    private static final int DEFAULT_STATUS_CODE = 401;

    public UserNotActivatedException() {
        super(DEFAULT_MESSAGE);
        statusCode = DEFAULT_STATUS_CODE;
    }

    /**
     * Constructs a <code>UserNotActivatedException</code> with the specified
     * message.
     *
     * @param msg the detail message
     */
    public UserNotActivatedException(String msg) {
        super(msg != null ? msg : DEFAULT_MESSAGE);
        statusCode = DEFAULT_STATUS_CODE;
    }

    /**
     * Constructs a <code>UserNotActivatedException</code> with the specified
     * message and root cause.
     *
     * @param msg the detail message
     * @param t   root cause
     */
    public UserNotActivatedException(String msg, Throwable t) {
        super(msg != null ? msg : DEFAULT_MESSAGE, t);
        statusCode = DEFAULT_STATUS_CODE;
    }

    public UserNotActivatedException(String msg, int code) {
        super(msg != null ? msg : DEFAULT_MESSAGE, code);
    }
}