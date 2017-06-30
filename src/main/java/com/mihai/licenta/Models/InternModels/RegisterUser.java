package com.mihai.licenta.Models.InternModels;

/**
 * Created by mihai on 21.06.2017.
 */
public class RegisterUser {

    public String username;
    public String email;
    public String password;
    public String photoUrl;
    public String fbID;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getFbID() {
        return fbID;
    }

    public void setFbID(String fbID) {
        this.fbID = fbID;
    }
}
