package com.books.spring.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.number.money.MonetaryAmountFormatter;

@Entity
@Table(name = "books")
@Getter
@Setter
public class Book {
    @Id
    @Column(name = "book_isbn")
    private Long isbn;

    @Column(name = "book_name")
    private String book_name;

    @Column(name = "book_length")
    private Long book_length;

    @Column(name = "chapter_amount", nullable = false)
    private int chapters;

    @Column(name = "price", nullable = false)
    private MonetaryAmountFormatter price;

}