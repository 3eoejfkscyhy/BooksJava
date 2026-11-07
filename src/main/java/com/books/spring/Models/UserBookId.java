
package com.books.spring.Models;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class UserBookId implements Serializable
{
 private Long user;
 private String book;
// public UserBookId(User user,Book book)
// {
//    this.user=user;
//    this.book=book;
// }
}