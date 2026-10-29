package com.books.spring.Models;

import jakarta.persistence.*;
import lombok.*;
import  java.util.*;
import  java.math.BigDecimal;


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
    private String bookName;

    @Column(name = "book_length",nullable = false)
    private Long bookLength;

    @Column(name = "chapter_amount") 
    private int chapters;

    @Column(name = "price", nullable = false)
    private BigDecimal price; 

    public enum Status {
        Not_Owned, Owned, Rented, Timed
    } //Temp values
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status bookStatus;
  
    @OneToMany(mappedBy = "book",orphanRemoval = true)
    private Set<Review> reviews = new HashSet<>();

    @ManyToMany(mappedBy = "books")
    private Set<Collection> collections = new HashSet<>();

    @ManyToMany(mappedBy = "isbns")
    private Set<Author> authors = new HashSet<>();

    @ManyToMany(mappedBy = "genreBooks")
    private Set<Genre> genres = new HashSet<>();

}