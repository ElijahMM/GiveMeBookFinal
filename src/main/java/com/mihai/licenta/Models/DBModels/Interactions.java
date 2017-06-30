package com.mihai.licenta.Models.DBModels;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * Created by mihai on 12.05.2017.
 */
@Entity
@Table(name = "INTERACTIONS")
public class Interactions {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long iId;

    /**
     * 1 - friend request
     * 2 - friend accepted
     * 3 - follow
     */
    @Column
    private Integer type;

    @Column
    private Long refId;


    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;


    public Long getiId() {
        return iId;
    }

    public void setiId(Long iId) {
        this.iId = iId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getRefId() {
        return refId;
    }

    public void setRefId(Long refId) {
        this.refId = refId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
