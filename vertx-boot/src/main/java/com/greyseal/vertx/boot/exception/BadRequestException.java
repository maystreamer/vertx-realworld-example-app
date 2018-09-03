package com.greyseal.vertx.boot.exception;

public class BadRequestException extends RestException {

    /**
     *
     */
    private static final long serialVersionUID = 505524577067688375L;
    /**
     *
     */
    private static final String DEFAULT_MESSAGE = "Bad request.";
    private static final int DEFAULT_STATUS_CODE = 400;

    public BadRequestException() {
        super(DEFAULT_MESSAGE);
        statusCode = DEFAULT_STATUS_CODE;
    }

    /**
     * Constructs a <code>BadRequestException</code> with the specified
     * message.
     *
     * @param msg the detail message
     */
    public BadRequestException(final String msg) {
        super(msg != null ? msg : DEFAULT_MESSAGE);
        statusCode = DEFAULT_STATUS_CODE;
    }

    /**
     * Constructs a <code>BadRequestException</code> with the specified
     * message and root cause.
     *
     * @param msg the detail message
     * @param t   root cause
     */
    public BadRequestException(String msg, Throwable t) {
        super(msg != null ? msg : DEFAULT_MESSAGE, t);
        statusCode = DEFAULT_STATUS_CODE;
    }

    /**
     * Constructs a <code>BadRequestException</code> with the specified
     * message and root cause.
     *
     * @param msg  the detail message
     * @param code http status code
     */
    public BadRequestException(String msg, int code) {
        super(msg != null ? msg : DEFAULT_MESSAGE, code);
    }
}