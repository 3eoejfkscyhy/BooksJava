package com.books.spring.Models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import  java.util.*;
import  java.math.BigDecimal;

@Entity
@Table(name = "books")
@AllArgsConstructor 
@NoArgsConstructor 
@Getter
@Setter
@Builder
public class Book {
    @Id
    @Column(name = "book_isbn", length = 13)
    private String isbn;

    @Column(name = "book_name", nullable = false)
    private String title;

    @Column(name = "book_length", nullable = false)
    private Long bookLength;

    @Column(name = "language", nullable = false)
    private String language;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "free_from")
    private LocalDateTime freeFrom;

    @Column(name = "free_until")
    private LocalDateTime freeUntil;

    public boolean isFree() {
        LocalDateTime now = LocalDateTime.now();

        return freeFrom != null && freeUntil != null && !now.isBefore(freeFrom) && now.isBefore(freeUntil);
    }

    @Builder.Default
    @OneToMany(mappedBy = "book", orphanRemoval = true)
    private Set<Review> reviews = new HashSet<>();

    @Builder.Default
    @ManyToMany
    @JoinTable(
            name = "book_authors",
            joinColumns = @JoinColumn(name = "book_isbn"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private Set<Author> bookAuthors = new HashSet<>();

    @Builder.Default
    @ManyToMany
    @JoinTable(
            name = "book_genres",
            joinColumns = @JoinColumn(name = "book_isbn"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private Set<Genre> bookGenres = new HashSet<>();

    @Builder.Default
    @ManyToMany(mappedBy = "books")
    private Set<Collection> collections = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "book")
    private Set<UserBooks> BookSide = new HashSet<>();

    @Builder.Default
    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;

}