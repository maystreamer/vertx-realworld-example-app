package io.greyseal.realworld.handler;

import com.greyseal.vertx.boot.Constant.MediaType;
import com.greyseal.vertx.boot.annotation.Protected;
import com.greyseal.vertx.boot.annotation.RequestMapping;
import com.greyseal.vertx.boot.exception.RestException;
import com.greyseal.vertx.boot.handler.BaseHandler;
import io.greyseal.realworld.auth.SessionUser;
import io.greyseal.realworld.dto.AppUserDTO;
import io.greyseal.realworld.dto.LoginDTO;
import io.greyseal.realworld.dto.UserSessionDTO;
import io.greyseal.realworld.exception.BadCredentialsException;
import io.greyseal.realworld.helper.AppUserHelper;
import io.greyseal.realworld.helper.Constants;
import io.greyseal.realworld.model.AppUser;
import io.greyseal.realworld.util.MapperUtil;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.reactivex.Single;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.DecodeException;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.UpdateOptions;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.core.buffer.Buffer;
import io.vertx.reactivex.ext.web.RoutingContext;
import org.bson.types.ObjectId;

import java.util.Date;

import static org.apache.commons.lang3.StringUtils.isBlank;


@RequestMapping(path = "/users")
public class AppUserHandler extends BaseHandler {
    private AppUserHelper appUserHelper = AppUserHelper.INSTANCE;

    public AppUserHandler(Vertx vertx) {
        super(vertx);
    }

    /**
     * This method is used to update the user model
     *
     * @param event
     */
    @Override
    @Protected
    @RequestMapping(method = HttpMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void handle(final RoutingContext event) {
        try {
            final Date date = new Date();
            final ObjectId userId = ((SessionUser) event.getDelegate().user()).getCurrentSession().getAppUserId();
            final JsonObject appUserJson = event.getBodyAsJson().getJsonObject("user");
            final AppUser appUser = Json.decodeValue(appUserJson.toString(), AppUserDTO.class).toAppUser();
            appUser.setUpdatedBy(userId);
            appUser.setUpdatedDate(date);
            final JsonObject query = new JsonObject().put("email", appUserJson.getString("email"));
            final UpdateOptions updateOptions = new UpdateOptions();
            updateOptions.setUpsert(true);
            appUserHelper.updateAppUser(query, appUser.toJson(), updateOptions)
                    .doOnSuccess(jsonObject -> {
                        event.setBody(Buffer.buffer(appUser.toJson().toString()));
                        event.response().setStatusCode(HttpResponseStatus.OK.code());
                        event.next();
                    }).doOnError(cause -> {
                event.fail(new RestException(new JsonObject().put("message", cause.getMessage()), HttpResponseStatus.INTERNAL_SERVER_ERROR.code()));
            }).subscribe();
        } catch (DecodeException dx) {
            event.fail(new RestException(new JsonObject().put("message", "Invalid json passed"), HttpResponseStatus.BAD_REQUEST.code()));
        } catch (Exception ex) {
            event.fail(ex);
        }
    }

    /**
     * This method is used to register the user model without providing auth token
     *
     * @param event
     */
    @RequestMapping(method = HttpMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void register(RoutingContext event) {
        try {
            final JsonObject appUserJson = event.getBodyAsJson().getJsonObject("user");
            final Date date = new Date();
            final AppUser appUserRequest = Json.decodeValue(appUserJson.toString(), AppUser.class);
            appUserHelper.doValidate(appUserRequest, true).flatMap(appUser -> {
                final ObjectId objectId = ObjectId.get();
                appUser.setCreatedDate(date);
                appUser.setUpdatedDate(date);
                appUser.setCreatedBy(objectId);
                appUser.setUpdatedBy(objectId);
                return appUserHelper.doCreate(appUser.toJson());
            }).flatMap(jsonObject -> {
                return Single.just(new JsonObject().put("id", jsonObject.getJsonObject("_id").getValue("$oid").toString()));
            }).doOnSuccess(result -> {
                event.setBody(Buffer.buffer(result.toString()));
                event.response().setStatusCode(HttpResponseStatus.CREATED.code());
                event.next();
            }).doOnError(cause -> {
                event.fail(new RestException(new JsonObject().put("message", cause.getMessage()), HttpResponseStatus.INTERNAL_SERVER_ERROR.code()));
            }).subscribe();
        } catch (DecodeException dx) {
            event.fail(new RestException(new JsonObject().put("message", "Invalid json passed"), HttpResponseStatus.BAD_REQUEST.code()));
        } catch (Exception ex) {
            event.fail(ex);
        }
    }

    /**
     * This method is used to login the user to the system
     *
     * @param event
     */
    @RequestMapping(path = "/login", method = HttpMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void login(final RoutingContext event) {
        try {
            final JsonObject loginDTOJson = event.getBodyAsJson().getJsonObject("user");
            final LoginDTO loginDTO = Json.decodeValue(loginDTOJson.toString(), LoginDTO.class);
            final String password = loginDTO.getPassword();
            final String email = loginDTO.getEmail();
            if (!isBlank(password) && !isBlank(email)) {
                appUserHelper.doAuthenticate(loginDTO)
                        .flatMap(appUser -> appUserHelper.createSession(appUser).flatMap(session -> {
                            final UserSessionDTO userSession = MapperUtil.map(appUser, UserSessionDTO.class);
                            userSession.setSessionToken(session.getToken());
                            return Single.just(userSession);
                        })).doOnSuccess(userSession -> {
                    final String sessionHeader = String.join(" ", Constants.AUTH_SCHEME,
                            userSession.getSessionToken());
                    event.response().putHeader(HttpHeaders.AUTHORIZATION.toString(), sessionHeader);
                    event.response().setStatusCode(HttpResponseStatus.OK.code());
                    event.setBody(Buffer.buffer(userSession.toString()));
                    event.next();
                }).doOnError(cause -> {
                    event.fail(new RestException(new JsonObject().put("message", cause.getMessage()), HttpResponseStatus.UNAUTHORIZED.code()));
                }).subscribe();
            } else {
                event.fail(new BadCredentialsException());
            }
        } catch (DecodeException dx) {
            LOGGER.error("Error while decoding ", dx);
            event.fail(new RestException("Bad Request", HttpResponseStatus.UNAUTHORIZED.code()));
        } catch (Exception ex) {
            LOGGER.error("Error while processing authentication ", ex);
            event.fail(new BadCredentialsException());
        }
    }

    /**
     * This method is used to get the current logged-in user
     *
     * @param event
     */
    @Protected
    @RequestMapping(path = "/current", method = HttpMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void currentUser(final RoutingContext event) {
        try {
            final Date date = new Date();
            final ObjectId userId = ((SessionUser) event.getDelegate().user()).getCurrentSession().getAppUserId();
            appUserHelper.doFindOneById(userId)
                    .doOnSuccess(appUser -> {
                        event.setBody(Buffer.buffer(AppUserDTO.toAppUserDTO(appUser.toJson()).toString()));
                        event.response().setStatusCode(HttpResponseStatus.OK.code());
                        event.next();
                    }).doOnError(cause -> {
                event.fail(new RestException(new JsonObject().put("message", cause.getMessage()), HttpResponseStatus.INTERNAL_SERVER_ERROR.code()));
            }).subscribe();
        } catch (DecodeException dx) {
            event.fail(new RestException(new JsonObject().put("message", "Invalid json passed"), HttpResponseStatus.BAD_REQUEST.code()));
        } catch (Exception ex) {
            event.fail(ex);
        }
    }
}