package com.books.spring.DTOs;

import java.util.Set;

import com.books.spring.Misc.BooksJavaConstants.ValidationMessages;
import com.books.spring.Misc.BooksJavaConstants.ValidationRules;

import jakarta.validation.constraints.*;

public record CollectionDTO( 
     Long collectionID,

    @NotBlank(message = ValidationMessages.COLLECTION_NAME_REQUIRED)
    @Size(max = ValidationRules.COLLECTION_NAME_MAX_LENGHT, message = ValidationMessages.COLLECTION_NAME_SIZE_VALID)
     String collectionName,

    @NotNull(message = ValidationMessages.USER_ID_REQUIRED)
    @Positive(message = ValidationMessages.USER_ID_VALID)
     Long userID,

     Set<String> isbns,

     Boolean isDeleted
){}