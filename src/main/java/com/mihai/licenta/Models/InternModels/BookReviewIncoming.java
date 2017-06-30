package com.mihai.licenta.Models.InternModels;

/**
 * Created by mihai on 16.06.2017.
 */
public class BookReviewIncoming {

    private Long bookID;
    private Long reviewID;


    public Long getBookID() {
        return bookID;
    }

    public void setBookID(Long bookID) {
        this.bookID = bookID;
    }

    public Long getReviewID() {
        return reviewID;
    }

    public void setReviewID(Long reviewID) {
        this.reviewID = reviewID;
    }
}
