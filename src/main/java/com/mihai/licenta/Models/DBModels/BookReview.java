package com.mihai.licenta.Models.DBModels;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

/**
 * Created by mihai on 07.06.2017.
 */
@Entity
public class BookReview {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idBookReview;

    @Column
    private String reviewContent;

    @Column
    private Long userID;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id")
    private Book book;


    public Long getIdBookReview() {
        return idBookReview;
    }

    public void setIdBookReview(Long idBookReview) {
        this.idBookReview = idBookReview;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
