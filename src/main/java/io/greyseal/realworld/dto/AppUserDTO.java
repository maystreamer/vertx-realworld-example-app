package io.greyseal.realworld.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.greyseal.realworld.model.AppUser;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppUserDTO {
    private String userName;
    private String bio;
    private String image;
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public AppUser toAppUser() {
        final AppUser appUser = new AppUser();
        appUser.setUserName(this.userName);
        appUser.setBio(this.bio);
        appUser.setImage(this.image);
        return appUser;
    }

    public static AppUserDTO toAppUserDTO(final JsonObject json) {
        return Json.decodeValue(json.toString(), AppUserDTO.class);
    }

    public JsonObject toJson() {
        return Json.encodeToBuffer(this).toJsonObject();
    }

    public String toString() {
        return toJson().toString();
    }
}
