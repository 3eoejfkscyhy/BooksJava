package com.books.spring.Models;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.hibernate.annotations.*;
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

    @Column (name = "headline", nullable = false, length = 40)
    private String headline;  
    //Limited size for headlines

    @Column (name = "body")
    private String body;  

    @Column(name = "stars", nullable = false)
    private BigDecimal stars;  

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "writtenBy", referencedColumnName = "user_id")
    private User writtenBy;  

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "book_isbn", nullable = false)
    private Book book;


    @Builder.Default
    @Column(name = "leftAt", nullable = false)
    private LocalDate leftAt = LocalDate.now();

    @Builder.Default
    @Column(name = "is_edited", nullable = false)
    private Boolean isEdited = false;

}
