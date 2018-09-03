package io.greyseal.realworld.exception;

import com.greyseal.vertx.boot.exception.RestException;

public class DuplicateEntityException extends RestException {

    private static final long serialVersionUID = 3086504192032342231L;
    private static final String DEFAULT_MESSAGE = "Record already exists";
    private static final int DEFAULT_STATUS_CODE = 400;

    public DuplicateEntityException() {
        super(DEFAULT_MESSAGE);
        statusCode = DEFAULT_STATUS_CODE;
    }

    /**
     * Constructs a <code>DuplicateEntityException</code> with the specified
     * message.
     *
     * @param msg the detail message
     */
    public DuplicateEntityException(String msg) {
        super(msg != null ? msg : DEFAULT_MESSAGE);
        statusCode = DEFAULT_STATUS_CODE;
    }

    /**
     * Constructs a <code>DuplicateEntityException</code> with the specified
     * message and root cause.
     *
     * @param msg the detail message
     * @param t   root cause
     */
    public DuplicateEntityException(String msg, Throwable t) {
        super(msg != null ? msg : DEFAULT_MESSAGE, t);
        statusCode = DEFAULT_STATUS_CODE;
    }

    public DuplicateEntityException(String msg, int code) {
        super(msg != null ? msg : DEFAULT_MESSAGE, code);
    }
}