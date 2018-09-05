package io.greyseal.realworld.handler;

import com.greyseal.vertx.boot.annotation.AuthProvider;
import com.greyseal.vertx.boot.exception.InvalidTokenException;
import com.greyseal.vertx.boot.exception.RestException;
import com.greyseal.vertx.boot.handler.BaseHandler;
import io.greyseal.realworld.auth.SessionUser;
import io.greyseal.realworld.helper.AppUserHelper;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.ext.web.RoutingContext;

@AuthProvider
public class AuthHandler extends BaseHandler {
    protected static Logger logger = LoggerFactory.getLogger(AuthHandler.class);
    private AppUserHelper appUserHelper = AppUserHelper.INSTANCE;

    public AuthHandler(Vertx vertx) {
        super(vertx);
    }

    //helper method
    public static AuthHandler create(final Vertx vertx) {
        return new AuthHandler(vertx);
    }

    /**
     * This method is used to authenticate any api which has @Protected annotation
     *
     * @param event
     */
    @Override
    public void handle(RoutingContext event) {
        System.out.println("AuthHandler called");
        final String authHeader = event.request().headers().get(HttpHeaders.AUTHORIZATION.toString());
        if (null == authHeader)
            throw new InvalidTokenException("Missing auth token");
        appUserHelper.doValidateSession(authHeader.trim())
                .doOnSuccess(session -> {
                    final SessionUser sessionUser = new SessionUser(session, AppUserHelper.INSTANCE);
                    event.getDelegate().setUser(sessionUser);
                    event.next();
                }).doOnError(cause -> {
            event.fail(new RestException(new JsonObject().put("message", cause.getMessage()), HttpResponseStatus.INTERNAL_SERVER_ERROR.code()));
        }).subscribe();
    }

}