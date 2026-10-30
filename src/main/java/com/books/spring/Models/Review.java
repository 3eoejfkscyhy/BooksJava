package com.books.spring.Models;

import java.math.BigDecimal;
import jakarta.persistence.*;
import lombok.*;


@Entity 
@Table(name= "reviews")
@AllArgsConstructor 
@NoArgsConstructor 
@Getter
@Setter 
@Builder
public class Review {
    @Id
    @Column (name = "review_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewID; 

    @Column (name = "headline", nullable = false)
    private String headline;  //Use for Seeder
    //Limited size for headlines

    @Column (name = "body")
    private String body;  //Use for Seeder

    @Column(name = "stars", nullable = false)
    private BigDecimal stars;  //Use for Seeder

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writtenBy", referencedColumnName = "user_id")
    private User writtenBy;  //Use for Seeder

    // @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "book_isbn", nullable = false)
    // private Book book;  //Use for Seeder
}
