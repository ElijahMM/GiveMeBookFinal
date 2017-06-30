package com.mihai.licenta.Models.DBModels;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

/**
 * Created by mihai on 12.05.2017.
 */
@Entity
@Table(name = "USER_PREFERENCES")
public class UserPreferences implements Comparable<UserPreferences> {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long pid;

    @Column
    private String pname;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;


    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int compareTo(UserPreferences o) {
        return this.getPname().compareTo(o.getPname());
    }
}
