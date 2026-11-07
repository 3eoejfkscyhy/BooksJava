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
@Builder
public class Genre {
 @Id 
 @Column(name = "genre_id")
 @GeneratedValue (strategy = GenerationType.IDENTITY)
 private Long genreID;  

 @Column (name = "genre_name", nullable = false)
 private String genreName; 

   @Builder.Default
   @ManyToMany(mappedBy = "bookGenres")
   private Set<Book> books = new HashSet<>();

    @Builder.Default
    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;
}
