package com.mihai.licenta.Models.DBModels;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.data.annotation.AccessType;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


/**
 * Created by mihai on 12.05.2017.
 */
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "BOOKS")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @AccessType(AccessType.Type.PROPERTY)
    private Long bId;


    @Column
    private String title;

    @Column
    private String author;

    @Column
    private String description;

    @Column
    private String cover_photo;

    @Column
    private Long uploaderID;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "book", orphanRemoval = true)
    private Set<Categories> categories;

    @JsonBackReference
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "book", orphanRemoval = true)
    private Set<Recommendations> recommendations = new HashSet<>();

    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "book", orphanRemoval = true)
    private Set<BookState> bookStates;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "book", orphanRemoval = true)
    private Set<BookReview> bookReviews;

    public Long getbId() {
        return bId;
    }

    public void setbId(Long bId) {
        this.bId = bId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Categories> getCategories() {
        return categories;
    }

    public void setCategories(Set<Categories> categories) {
        this.categories = categories;
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

    public Set<BookReview> getBookReviews() {
        return bookReviews;
    }

    public void setBookReviews(Set<BookReview> bookReviews) {
        this.bookReviews = bookReviews;
    }

    public String getCover_photo() {
        return cover_photo;
    }

    public void setCover_photo(String cover_photo) {
        this.cover_photo = cover_photo;
    }

    public Long getUploaderID() {
        return uploaderID;
    }

    public void setUploaderID(Long uploaderID) {
        this.uploaderID = uploaderID;
    }
}

