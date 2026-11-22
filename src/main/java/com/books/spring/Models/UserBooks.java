package com.books.spring.Models;

import lombok.*;
import org.hibernate.annotations.*;
import com.books.spring.Misc.UserBookId;
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
 @OnDelete(action = OnDeleteAction.CASCADE)
 @ManyToOne
 @JoinColumn (name = "user_id",referencedColumnName = "user_id")
 private User user;

 @Id
 @OnDelete(action = OnDeleteAction.CASCADE)
 @ManyToOne
 @JoinColumn (name = "book_isbn", referencedColumnName = "book_isbn")
 private Book book;

     public enum Status {
         Wishlisted,PreviouslyOwned, Rented, Owned, Read
    }
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status readingStatus;

    public Long lastReadTo;

    public String readingPercentage;

    @Builder.Default
    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;
}






