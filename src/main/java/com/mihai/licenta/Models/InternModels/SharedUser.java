package com.mihai.licenta.Models.InternModels;

import com.mihai.licenta.Models.DBModels.BookState;
import com.mihai.licenta.Models.DBModels.Interactions;

import java.util.Set;

/**
 * Created by mihai on 13.06.2017.
 */
public class SharedUser {

    private Long uid;
    private String username;
    private String photoUrl;
    private String fbID;
    private Set<Interactions> friends;
    private Set<BookState> bookStates;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public Set<Interactions> getInteractions() {
        return friends;
    }

    public void setInteractions(Set<Interactions> friends) {
        this.friends = friends;
    }

    public Set<BookState> getBookStates() {
        return bookStates;
    }

    public void setBookStates(Set<BookState> bookStates) {
        this.bookStates = bookStates;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }
}
