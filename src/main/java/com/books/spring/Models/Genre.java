package com.books.spring.Models;

import lombok.*;
import java.util.*;
import jakarta.persistence.*;

@Entity 
@Table(name = "genres")
@AllArgsConstructor 
@NoArgsConstructor 
@Getter 
@Setter
public class Genre {
 @Id 
 @Column(name = "genre_id")
 @GeneratedValue (strategy = GenerationType.IDENTITY)
 private Long genreID;

 @Column (name = "genre_name", nullable = false)
 private String genreName;

 @ManyToMany
 @JoinTable(
    name = "book_genres",
    joinColumns = @JoinColumn(name = "book_isbn"),
    inverseJoinColumns = @JoinColumn (name="genre_id")
 )    
 private Set<Book> genreBooks = new HashSet<>();
}
