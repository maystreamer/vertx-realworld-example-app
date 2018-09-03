package io.greyseal.realworld.model;

import io.greyseal.realworld.util.TokenUtil;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
import org.bson.types.ObjectId;

import java.util.Date;

@DataObject(generateConverter = true)
public class Session extends Base {

    public static final String DB_TABLE = "sessions";
    private static final long serialVersionUID = -7710657370320218440L;

    private ObjectId _id;
    private String token;
    private ObjectId appUserId;

    //Mandatory for data objects
    public Session(JsonObject jsonObject) {
        SessionConverter.fromJson(jsonObject, this);
        fromBaseJson(jsonObject, this);
        final JsonObject json = jsonObject.getJsonObject("appUserId");
        if (null != json) {
            this.setAppUserId(new ObjectId(jsonObject.getJsonObject("appUserId").getValue("$oid").toString()));
        }
    }

    public Session() {
    }

    public static Session buildSession(final AppUser appUser) {
        final Date dt = new Date();
        final Session session = new Session();
        session.setToken(TokenUtil.getUUID());
        session.setAppUserId(appUser.get_id());
        session.setCreatedBy(appUser.get_id());
        session.setCreatedDate(dt);
        session.setIsActive(true);
        session.setUpdatedBy(appUser.get_id());
        session.setUpdatedDate(dt);
        return session;
    }

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public ObjectId getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(ObjectId appUserId) {
        this.appUserId = appUserId;
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        SessionConverter.toJson(this, json);
        if (this.getAppUserId() != null) {
            json.put("appUserId", new JsonObject().put("$oid", this.getAppUserId().toHexString()));
        }
        toBaseJson(this, json);
        return json;
    }
}