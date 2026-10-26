package com.books.spring.Models;

import jakarta.persistence.*;
import lombok.*;


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
    private Long id;

    @Column(name = "first_name", nullable = false, length = 20)
    private String firstName;

    @Column(name = "last_name", length = 20)
    private String lastName;

    @Column(name = "age", nullable = false)
    private int age;

    @Column(name = "booksWritten", nullable = false)
    private int booksWritten;

}
