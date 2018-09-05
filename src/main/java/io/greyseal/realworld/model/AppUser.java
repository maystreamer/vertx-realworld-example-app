package io.greyseal.realworld.model;

import com.greyseal.vertx.boot.exception.RestException;
import io.greyseal.realworld.util.PasswordStorageUtil;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

import java.util.Date;

@DataObject(generateConverter = true)
public class AppUser extends Base {

    public static final String DB_TABLE = "app_users";
    private static final long serialVersionUID = -8946372748386633798L;

    private String userName;
    private String email;
    private String password;
    private String salt;
    private String bio;
    private String image;

    //Mandatory for data objects
    public AppUser(JsonObject jsonObject) {
        AppUserConverter.fromJson(jsonObject, this);
        fromBaseJson(jsonObject, this);
    }

    //for api handers
    public AppUser() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? email : email.trim().toLowerCase();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        AppUserConverter.toJson(this, json);
        toBaseJson(this, json);
        return json;
    }

    public AppUser toNewAppUser(boolean isSignUpUser) {
        String[] passwordAndSalt = PasswordStorageUtil.encrypt(this.getPassword());
        if (null != passwordAndSalt && passwordAndSalt.length == 2) {
            final Date dt = new Date();
            this.setSalt(passwordAndSalt[1]);
            this.setCreatedDate(dt);
            //this.setIsActive(!isSignUpUser);
            this.setIsActive(isSignUpUser);
            this.setUpdatedDate(dt);
            this.setPassword(passwordAndSalt[0]);
            return this;
        }
        throw new RestException("Error while building AppUser");
    }
}