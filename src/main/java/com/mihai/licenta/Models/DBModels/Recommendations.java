package com.mihai.licenta.Models.DBModels;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;

/**
 * Created by mihai on 12.05.2017.
 */
@Entity
@Table(name = "RECOMMENDATIONS")
public class Recommendations {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long rid;



    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id")
    private Book book;


    public Long getRid() {
        return rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
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



    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof Recommendations)) return false;
        Recommendations arg = (Recommendations) obj;
        if (this.getBook().getbId().equals(arg.getBook().getbId()) && this.getUser().getUid().equals(arg.getUser().getUid())) {
            return false;
        }
        return true;
    }
}
