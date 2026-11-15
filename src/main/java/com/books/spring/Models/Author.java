package com.books.spring.Models;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.*;
import org.hibernate.annotations.*;


@Entity
@Table(name = "authors")
@AllArgsConstructor 
@NoArgsConstructor 
@Getter
@Setter
@Builder 
public class Author {
    @Id
    @Column(name = "author_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long authorID;

    @Column(name = "first_name", nullable = false, length = 20)
    private String firstName;  

    @Column(name = "last_name", length = 20)
    private String lastName;  

    @Column(name = "age", nullable = false)
    private LocalDate age;  

    @Column(name = "booksWritten", nullable = false)
    private int booksWritten;  
 
    @Builder.Default
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToMany(mappedBy = "bookAuthors")
    private Set<Book> books = new HashSet<>();

    @Builder.Default
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;
}
