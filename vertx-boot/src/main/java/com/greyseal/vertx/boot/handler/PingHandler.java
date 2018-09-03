package com.greyseal.vertx.boot.handler;

import com.greyseal.vertx.boot.Constant.ErrorMessage;
import com.greyseal.vertx.boot.Constant.MediaType;
import com.greyseal.vertx.boot.annotation.Protected;
import com.greyseal.vertx.boot.annotation.RequestMapping;
import com.greyseal.vertx.boot.exception.BadRequestException;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.DecodeException;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.core.buffer.Buffer;
import io.vertx.reactivex.ext.web.RoutingContext;

/**
 * Sample handler to showcase the library usage for CRUD operations. Then CRUD operations can be called like the following:
 * <ul><li>{@link #handle(RoutingContext)} in order to show the HttpGet request-response ;</li></ul>
 * <ul><li>{@link #doPost(RoutingContext)} in order to show the HttpPost request-response ;</li></ul>
 * <ul><li>{@link #doPut(RoutingContext)} in order to show the HttpPut request-response ;</li></ul>
 * <ul><li>{@link #doDelete(RoutingContext)} in order to show the HttpDelete request-response ;</li></ul>
 * <p>
 * Annotations used are:
 * <ul><li>{@Protected} is used to make the api private. API needs a valid session token to perform the intended action ;</li></ul>
 * <ul><li>{@RequestMapping} is used to set the HttpMethod, MediaType to receive the request body and to send the response body  ;</li></ul>
 *
 * @author Greyseal
 * @version 0.1
 * @since vertx-boot-0.1
 */
@RequestMapping(path = "/ping")
public class PingHandler extends BaseHandler {

    public PingHandler(Vertx vertx) {
        super(vertx);
    }


    /**
     * Allows to perform a HttpGet request-response.
     *
     * @return HttpResponse either success or fail
     */
    @Override
    @Protected //used to protect the API
    @RequestMapping(method = HttpMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void handle(RoutingContext ctx) {
        try {
            JsonObject result;
            result = new JsonObject().put("status", "OK");
            ctx.setBody(Buffer.buffer(result.toString()));
            ctx.response().putHeader("Custom", "header");
            ctx.next();
        } catch (Exception ex) {
            ctx.fail(ex);
        }
    }

    /**
     * Allows to perform a HttpPost request-response.
     *
     * @return HttpResponse either success or fail
     */
    @RequestMapping(method = HttpMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void doPost(RoutingContext ctx) {
        try {
            final JsonObject params = ctx.getBodyAsJson();
            JsonObject result;
            result = new JsonObject().put("message", HttpMethod.POST + " called");
            ctx.setBody(Buffer.buffer(result.toString()));
            ctx.response().putHeader("Custom", "header");
            ctx.next();
        } catch (DecodeException ex) {
            LOGGER.error(ErrorMessage.JSON_DECODE_EXCEPTION, ex);
            ctx.fail(new BadRequestException(ErrorMessage.JSON_DECODE_EXCEPTION));
        } catch (Exception ex) {
            ctx.fail(ex);
        }
    }

    /**
     * Allows to perform a HttpPut request-response.
     *
     * @return HttpResponse either success or fail
     */
    @RequestMapping(method = HttpMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void doPut(RoutingContext ctx) {
        try {
            final JsonObject params = ctx.getBodyAsJson();
            JsonObject result;
            result = new JsonObject().put("message", HttpMethod.PUT + " called");
            ctx.setBody(Buffer.buffer(result.toString()));
            ctx.response().putHeader("Custom", "header");
            ctx.next();
        } catch (DecodeException ex) {
            LOGGER.error(ErrorMessage.JSON_DECODE_EXCEPTION, ex);
            ctx.fail(new BadRequestException(ErrorMessage.JSON_DECODE_EXCEPTION));
        } catch (Exception ex) {
            ctx.fail(ex);
        }
    }

    /**
     * Allows to perform a HttpDelete request-response.
     *
     * @return HttpResponse either success or fail
     */
    @RequestMapping(method = HttpMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void doDelete(RoutingContext ctx) {
        try {
            final Long id = Long.parseLong(ctx.request().getParam("id"));
            JsonObject result;
            result = new JsonObject().put("message", HttpMethod.DELETE + " called");
            ctx.setBody(Buffer.buffer(result.toString()));
            ctx.response().putHeader("Custom", "header");
            ctx.next();
        } catch (DecodeException ex) {
            LOGGER.error(ErrorMessage.JSON_DECODE_EXCEPTION, ex);
            ctx.fail(new BadRequestException(ErrorMessage.JSON_DECODE_EXCEPTION));
        } catch (NumberFormatException nx) {
            LOGGER.error(ErrorMessage.JSON_DECODE_EXCEPTION, nx);
            ctx.fail(new BadRequestException(ErrorMessage.NUMBER_FORMAT_EXCEPTION));
        } catch (Exception ex) {
            LOGGER.error(ErrorMessage.GENERIC_MESSAGE, ex);
            ctx.fail(ex);
        }
    }
}