package com.books.spring.Models;

import lombok.*;
import jakarta.persistence.*;

@Entity
@IdClass(UserBookId.class) 
@Table(name = "user_books")
@AllArgsConstructor 
@NoArgsConstructor 
@Getter 
@Setter
@Builder
public class UserBooks{
 @Id
 @ManyToOne
 @JoinColumn (name = "user_id",referencedColumnName = "user_id")
 private User user;

 @Id
 @ManyToOne
 @JoinColumn (name = "book_isbn",referencedColumnName = "book_isbn")
 private Book book;

     public enum Status {
         Wanted, Owned, Read
    } //Temp values
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status readingStatus;
}






