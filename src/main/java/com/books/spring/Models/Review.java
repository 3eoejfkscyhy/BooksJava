package com.books.spring.Models;

import jakarta.persistence.*;
import lombok.*;


@Entity 
@Table(name= "reviews")
@AllArgsConstructor 
@NoArgsConstructor 
@Getter
@Setter 
public class Review {
    @Id
    @Column (name = "review_id")
    private Long reviewID;

    @Column (name = "headline", nullable = false)
    private String headline;
    //Limited size for headlines

    @Column (name = "body")
    private String body;

     public enum Stars
     {
        Zero,ZeroHalf,One,OneHalf,Two,TwoHalf,Three,ThreeHalf,Four,FourHalf,Five
     }// Temp values
     @Enumerated(EnumType.STRING)
    @Column(name = "stars", nullable = false)
    private Stars stars;

    // @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn (name = "book_isbn",nullable=false)
    // private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writtenBy",referencedColumnName = "user_id")
    private User writtenBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_isbn", nullable = false)
    private Book book;
}
