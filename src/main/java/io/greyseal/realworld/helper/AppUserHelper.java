package io.greyseal.realworld.helper;

import com.greyseal.vertx.boot.exception.InvalidTokenException;
import com.greyseal.vertx.boot.exception.RestException;
import io.greyseal.realworld.dto.LoginDTO;
import io.greyseal.realworld.exception.BadCredentialsException;
import io.greyseal.realworld.exception.NotFoundException;
import io.greyseal.realworld.exception.UserNotActivatedException;
import io.greyseal.realworld.model.AppUser;
import io.greyseal.realworld.model.Session;
import io.greyseal.realworld.util.PasswordAuthenticator;
import io.greyseal.realworld.verticle.DatabaseVerticle;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.reactivex.Single;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.AuthProvider;
import io.vertx.ext.auth.User;
import io.vertx.ext.mongo.FindOptions;
import io.vertx.ext.mongo.MongoClientUpdateResult;
import io.vertx.ext.mongo.UpdateOptions;
import org.bson.types.ObjectId;

import static org.apache.commons.lang3.StringUtils.isBlank;

public enum AppUserHelper implements AuthProvider {
    INSTANCE;

    public Single<AppUser> doValidate(final AppUser appUser, boolean isSignUpUser) {
        final String email = appUser.getEmail();
        final String password = appUser.getPassword();
        final String userName = appUser.getUserName();
        final JsonObject errors = new JsonObject();
        boolean hasError = false;

        if (isBlank(userName) && isSignUpUser) {
            hasError = true;
            errors.put("userName", "User name is required");
        }
        if (isBlank(email) && isSignUpUser) {
            hasError = true;
            errors.put("email", "Email is required");
        }
        if (isBlank(password) && isSignUpUser) {
            hasError = true;
            errors.put("password", "Password is required");
        }
        if (hasError) {
            final JsonObject error = new JsonObject().put("errors", errors);
            throw new RestException(error, HttpResponseStatus.BAD_REQUEST.code());
        }
        return Single.just(appUser.toNewAppUser(isSignUpUser));
    }

    public Single<JsonObject> doCreate(final JsonObject appUser) {
        return DatabaseVerticle.rxDBService.rxInsertOne(AppUser.DB_TABLE, appUser).map(result -> {
            appUser.put("_id", new JsonObject().put("$oid", result));
            return appUser;
        });
    }

    public Single<JsonObject> updateAppUser(final JsonObject query, final JsonObject toUpdate, final UpdateOptions updateOptions) {
        final JsonObject _toUpdate = new JsonObject().put("$set", toUpdate.copy());
        return DatabaseVerticle.rxDBService.rxUpsert(AppUser.DB_TABLE, query, _toUpdate, updateOptions).map(result -> {
            if (null == result)
                throw new NotFoundException();
            return result.toJson();
        });
    }

    public Single<AppUser> doAuthenticate(final LoginDTO creds) {
        final JsonObject query = new JsonObject().put("email", creds.getEmail());
        return DatabaseVerticle.rxDBService.rxFindOne(AppUser.DB_TABLE, query, null).map(result -> {
            if (null == result)
                throw new BadCredentialsException();
            final AppUser _user = new AppUser(result);
            if (_user.getIsActive().booleanValue() == false)
                throw new UserNotActivatedException();
            _user.set_id(new ObjectId(result.getString("_id")));
            final boolean isPasswordOk = PasswordAuthenticator.isPasswordOk(creds.getPassword(), _user.getPassword(),
                    _user.getSalt());
            if (!isPasswordOk) {
                throw new BadCredentialsException();
            }
            return _user;
        });
    }

    public Single<Session> createSession(final AppUser appUser) {
        final Session session = Session.buildSession(appUser);
        return DatabaseVerticle.rxDBService.rxInsertOne(Session.DB_TABLE, session.toJson()).map(result -> {
            session.set_id(new ObjectId(result));
            return session;
        });
    }

    public Single<Session> doValidateSession(final String authHeader) {
        final String token = parseSessionToken(authHeader);
        final JsonObject query = new JsonObject().put("token", token).put("isActive", true);
        final JsonObject fields = new JsonObject().put("_id", true);
        fields.put("appUserId", true);
        return DatabaseVerticle.rxDBService.rxFindOne(Session.DB_TABLE, query, fields).map(result -> {
            if (null == result)
                throw new InvalidTokenException();
            return new Session(result);
        });
    }

    public Single<JsonObject> doLogoutSession(final String authHeader, final FindOptions findOptions, final UpdateOptions updateOptions) {
        String token = parseSessionToken(authHeader);
        JsonObject query = new JsonObject().put("token", token);
        JsonObject toUpdate = new JsonObject();
        toUpdate.put("$set", new JsonObject().put("isActive", false));
        return DatabaseVerticle.rxDBService.rxFindOneAndUpdate(Session.DB_TABLE, query, toUpdate, findOptions, updateOptions).map(result -> {
            if (null == result)
                throw new InvalidTokenException();
            return result;
        });
    }

    private String parseSessionToken(final String authHeader) {
        String[] parts = authHeader.split(" ");
        String sscheme = parts[0];
        if (!Constants.AUTH_SCHEME.equals(sscheme)) {
            throw new InvalidTokenException("Invalid token scheme. Should be BEARER");
        }
        if (parts.length < 2) {
            throw new InvalidTokenException();
        }
        return parts[1];
    }

    @Override
    public void authenticate(JsonObject authInfo, Handler<AsyncResult<User>> resultHandler) {

    }
}