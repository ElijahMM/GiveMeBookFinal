package com.mihai.licenta.Models.DBModels;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

/**
 * Created by mihai on 12.05.2017.
 */

@Entity
@Table(name = "CATEGORIES")
public class Categories {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long cid;

    @Column
    private String name;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;


    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
