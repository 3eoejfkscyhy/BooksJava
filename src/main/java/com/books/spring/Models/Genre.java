package com.books.spring.Models;

import lombok.*;
import java.util.*;
import org.hibernate.annotations.*;
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
   @OnDelete(action = OnDeleteAction.CASCADE)
   @ManyToMany(mappedBy = "bookGenres")
   private Set<Book> books = new HashSet<>();

}
