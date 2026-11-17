
package com.books.spring.Misc;

import java.io.Serializable;
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
}