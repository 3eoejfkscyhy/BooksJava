package com.books.spring.Models;

import lombok.*;
import java.util.*;
import jakarta.persistence.*;

@Entity 
@Table(name="collections")
@AllArgsConstructor 
@NoArgsConstructor 
@Getter 
@Setter 
@Builder
public class Collection {
    @Id 
    @Column(name = "collection_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long collectionID;

    @Column (name = "collection_name", nullable = false )
    private String collectionName; 
   
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @Builder.Default
    @ManyToMany 
    @JoinTable(
        name = "collection_books",
        joinColumns = @JoinColumn (name = "collection_id"),
        inverseJoinColumns = @JoinColumn(name = "book_isbn")
    )
    private Set<Book> books = new HashSet<>();
    
}
