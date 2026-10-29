package com.books.spring.Models;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;


@Entity
@Table(name = "authors")
@AllArgsConstructor 
@NoArgsConstructor 
@Getter
@Setter
public class Author {
    @Id
    @Column(name = "author_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long authorID;

    @Column(name = "first_name", nullable = false, length = 20)
    private String firstName;   //Use for Seeder

    @Column(name = "last_name", length = 20)
    private String lastName;   //Use for Seeder

    @Column(name = "age", nullable = false)
    private int age;   //Use for Seeder

    @Column(name = "booksWritten", nullable = false)
    private int booksWritten;   //Use for Seeder

    @ManyToMany 
    @JoinTable (
        name = "book_authors",
        joinColumns = @JoinColumn (name = "book_isbn"), 
        inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private  Set<Book> isbns = new HashSet<>();
    //Ask about Sets in Seeder

}
