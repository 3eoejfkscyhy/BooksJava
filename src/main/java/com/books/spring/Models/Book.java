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
    //Ask about if this will be used for seeder

    @Column(name = "book_name", nullable = false)
    private String bookName;
    //Use for Seeder

    @Column(name = "book_length",nullable = false)
    private Long bookLength;
    //Use for Seeder

    @Column(name = "chapter_amount") 
    private int chapters;
    //Use for Seeder

    @Column(name = "price", nullable = false)
    private BigDecimal price; 
    //Use for Seeder

    public enum Status {
        Not_Owned, Owned, Rented, Timed
    } //Temp values
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status bookStatus;
    //Use for Seeder
  
    @OneToMany(mappedBy = "book",orphanRemoval = true)
    private Set<Review> reviews = new HashSet<>();
    //Ask about Sets in Seeder

    @ManyToMany(mappedBy = "books")
    private Set<Collection> collections = new HashSet<>();
    //Ask about Sets in Seeder

    @ManyToMany(mappedBy = "isbns")
    private Set<Author> authors = new HashSet<>();
    //Ask about Sets in Seeder

    @ManyToMany(mappedBy = "genreBooks")
    private Set<Genre> genres = new HashSet<>();
    //Ask about Sets in Seeder

}