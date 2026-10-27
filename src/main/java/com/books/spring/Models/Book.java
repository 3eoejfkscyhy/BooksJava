package com.books.spring.Models;

import jakarta.persistence.*;
import lombok.*;
import  java.util.*;
// import org.springframework.format.number.money.MonetaryAmountFormatter;

@Entity
@Table(name = "books")
@AllArgsConstructor 
@NoArgsConstructor 
@Getter
@Setter
public class Book {
    @Id
    @Column(name = "book_isbn")
    private Long isbn;

    @Column(name = "book_name", nullable = false)
    private String book_name;

    @Column(name = "book_length",nullable = false)
    private Long book_length;

    @Column(name = "chapter_amount") 
    private int chapters;

    @Column(name = "price", nullable = false)
    private float price; 
    //Replaced for testing purposes, find money alternative


    public enum Status {
        Not_Owned, Owned, Rented, Timed
    } //Temp values
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status bookStatus;
  
    // @OneToMany(mappedBy = "book",orphanRemoval = true)
    // private Set<Review> reviews = new HashSet<>();

    @ManyToMany(mappedBy = "books")
    private Set<Collection> collections = new HashSet<>();

    @ManyToMany(mappedBy = "authorBooks")
    private Set<Author> authors = new HashSet<>();

    @ManyToMany(mappedBy = "genreBooks")
    private Set<Genre> genres = new HashSet<>();

}