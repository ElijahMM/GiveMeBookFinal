package com.mihai.licenta.Models.InternModels;

/**
 * Created by mihai on 15.06.2017.
 */
public class BookStateIncoming {

    private Long userID;
    private Long bookID;

    private Integer type;

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public Long getBookID() {
        return bookID;
    }

    public void setBookID(Long bookID) {
        this.bookID = bookID;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
