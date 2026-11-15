package com.books.spring.Mappers;

import com.books.spring.DTOs.UserDTO;
import com.books.spring.Models.Review;
import com.books.spring.Models.Collection;
import com.books.spring.Models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {
  @Mapping(target = "reviewIDs", source = "reviews")
    @Mapping(target = "collectionIDs", source = "collections")
    UserDTO toDto(User user);

    @Mapping(target = "userID", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "UserSide", ignore = true)
    @Mapping(target = "collections", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    User toEntity(UserDTO dto);

    default Long reviewToID(Review review) { return review.getReviewID(); }
    default Long collectionToID(Collection collection) { return collection.getCollectionID(); }

    @Named("activeUsers")
    default Set<UserDTO> toActiveDtos(Set<User> users) {
        return users.stream()
                .filter(user-> !user.getIsDeleted())
                .map(this::toDto)
                .collect(Collectors.toSet());
    }

}