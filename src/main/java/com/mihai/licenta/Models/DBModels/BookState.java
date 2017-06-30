package com.mihai.licenta.Models.DBModels;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;

/**
 * Created by mihai on 12.05.2017.
 */
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "BOOKS_STATE")
public class BookState {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long bsId;

    @Column
    private Integer type;


    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;


    public Long getBsId() {
        return bsId;
    }

    public void setBsId(Long bsId) {
        this.bsId = bsId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
