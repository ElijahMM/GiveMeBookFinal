package com.mihai.licenta.Models.DBModels;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

/**
 * Created by mihai on 12.05.2017.
 */
@Entity
@Table(name = "USER")
public class User {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long uid;
    @Column
    private String username;
    @Column
    private String password;
    @Column
    private String email;
    @Column
    private Integer type;
    @Column
    private String photoUrl;
    @Column
    private String token;
    @Column
    private Date createdAt;
    @Column
    private Date updatedAt;
    @Column
    private String fbID;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
    private Set<UserPreferences> preferences;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
    private Set<Interactions> interactions;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
    private Set<Recommendations> recommendations;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
    private Set<BookState> bookStates;


    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getFbID() {
        return fbID;
    }

    public void setFbID(String fbID) {
        this.fbID = fbID;
    }

    public Set<UserPreferences> getPreferences() {
        return preferences;
    }

    public void setPreferences(Set<UserPreferences> preferences) {
        this.preferences = preferences;
    }

    public Set<Interactions> getInteractions() {
        return interactions;
    }

    public void setInteractions(Set<Interactions> interactions) {
        this.interactions = interactions;
    }

    public Set<Recommendations> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(Set<Recommendations> recommendations) {
        this.recommendations = recommendations;
    }

    public Set<BookState> getBookStates() {
        return bookStates;
    }

    public void setBookStates(Set<BookState> bookStates) {
        this.bookStates = bookStates;
    }
}
