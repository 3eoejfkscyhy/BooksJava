package com.books.spring.Models;

import jakarta.persistence.*;
import lombok.*;
// import org.springframework.format.number.money.MonetaryAmountFormatter;

@Entity
@Table(name = "books")
@AllArgsConstructor 
@NoArgsConstructor 
@Getter
@Setter
public class Book {
    @Id
    @Column(name = "book_isbn")
    private Long isbn;

    @Column(name = "book_name", nullable = false)
    private String book_name;

    @Column(name = "book_length",nullable = false)
    private Long book_length;

    @Column(name = "chapter_amount") 
    private int chapters;

    @Column(name = "price", nullable = false)
    private float price; 
    //Replaced for testing purposes

}