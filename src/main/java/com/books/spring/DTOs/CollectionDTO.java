package com.books.spring.DTOs;

import java.util.Set;
import jakarta.validation.constraints.*;

public record CollectionDTO( 
     Long collectionID,

    @NotBlank(message = "Collection name is required")
    @Size(max = 200, message = "Collection name must not exceed 200 characters")
     String collectionName,

    @NotNull(message = "User ID is required")
    @Positive(message = "User ID must be valid")
     Long userID,

     Set<String> isbns,

     Boolean isDeleted
){}