
package com.books.spring.Models;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;

@Embeddable 
@NoArgsConstructor 
public class UserBookId implements Serializable
{
 private User user;
 private Book book;
 public UserBookId(User user,Book book)
 {
    this.user=user;
    this.book=book;
 }
}