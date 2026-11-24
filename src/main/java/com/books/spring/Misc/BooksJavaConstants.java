package com.books.spring.Misc;

public final class BooksJavaConstants {
    private BooksJavaConstants() {}
    public static final class ExceptionMessages
    {
        public static final String CANT_UPDATE_WISHLISTED_PREVIOUSLY_OWNED_LAST_READ_PAGE = "Cannot change the last read page of a Wishlisted/Previously Owned book";
        public static final String ONLY_EBOOK_CAN_BE_PURCHASED = "Only Ebooks can be purchased through the application";
        public static final String INSUFFICIENT_BALANCE = "Insufficient balance";
        public static final String GIVEN_READ_PAGE_MORE_THAN_AVAILABLE = "The given page that was last read was bigger than the amount of pages there are";
        public static final String BOOK_IS_ALREADY_OWNED_BY_USER = "This user already owns this book";
        public static final String USERNAME_IS_IN_USE = "Username is already in use.";
        public static final String EMAIL_IS_IN_USE = "Email is already in use.";
        public static final String DEPOSIT_MUST_BE_BIGGER_THAN_ZERO = "Deposit amount must be greater than zero";
        public static final String USER_BOOK_RELATIONSHIP_NOT_FOUND = "User-book relationship not found";
        public static final String USER_HAS_BOOK_IN_READING_LIST = "This user already has this book in their reading list";
        public static final String GENRE_EXIST = "Genre already exists";
        public static final String FREE_PERIOD_MUST_BEGIN_BEFORE_END =  "The free period must begin before it ends";
        public static final String BOOK_TYPE_REQUIRED =  "Book type is required";
        public static final String BOOK_HAS_RENTAL_PRICE =  "E-books must have a rental price";
        public static final String PHYSICAL_BOOKS_CANT_BE_RENTED =  "Physical books cannot be rentable";
        public static final String ONLY_EBOOKS_ARE_RENTABLE =  "Only Ebooks can be rented through the application";
        public static final String INSUFFICIENT_BALANCE_TO_RENT =  "Insufficient balance to rent this book";
        public static final String USER_HAS_BOOK_RENTED_ALREADY =  "This user has an active rental for this book already";
        public static final String USER_ALREADY_HAS_RETURNED_BOOK =  "This rental has already been returned by this user";
        public static final String CANNOT_DELETE_ACTIVE_RENTAL =  "Cannot delete an active rental";
        public static final String GIVEN_USER_NOT_FOUND = "User with the ID %d could not be found";
        public static final String GIVEN_BOOK_NOT_FOUND = "Book %s could not be found";
        public static final String GIVEN_REVIEW_FOR_BOOK_NOT_FOUND =  "Review for book %s could not be found";
        public static final String GIVEN_REVIEW_NOT_FOUND =  "Review with the ID %d could not be found";
        public static final String GIVEN_GENRE_NOT_FOUND =  "Genre with the ID %d could not be found";
        public static final String GIVEN_COLLECTION_NOT_FOUND =  "Collection with the ID %d could not be found";
        public static final String GIVEN_AUTHOR_NOT_FOUND =  "Author with the ID %d could not be found";
        public static final String GIVEN_RENTAL_NOT_FOUND =  "Rental with the ID %d could not be found";

        private ExceptionMessages() {}
    }


    public static final class ValidationMessages
    {

        public static final String FIRST_NAME_REQUIRED =  "First name is required";
        public static final String BOOKS_WRITTEN_AMOUNT =  "Number of written books is required";
        public static final String AUTHOR_DOB_REQUIRED =  "Author DOB is required";
        public static final String AUTHOR_DOB_IN_PAST =  "The date of birth must be in the past";
        public static final String FIRST_NAME_LENGHT_SIZE =  "First name must be between 2 and 20 characters";
        public static final String LAST_NAME_LENGHT_SIZE =  "Last name must be between 2 and 20 characters";

        public static final String ISBN_VALID_FORMAT =  "ISBN must contain 13 digits and start with 978 or 979";
        public static final String BOOK_TITLE_REQUIRED =  "Book title is required";
        public static final String BOOK_LENGHT_VALID =  "Book length has to be valid";
        public static final String LANGUAGE_MUST_BE_LATIN_LETTERS =  "The language must be written with latin letters";
        public static final String PRICE_REQUIRED =  "Price is mandatory";
        public static final String BOOK_TYPE_REQUIRED =  "Book type is required";
        public static final String PRICE_POSITIVE =  "Price cannot be negative";
        public static final String RENTAL_PRICE_POSITIVE =  "Rental price cannot be negative";

        public static final String USER_ID_REQUIRED =  "User ID is required";
        public static final String USER_ID_VALID =  "User ID must be valid";

        public static final String BALANCE_POSITIVE =  "The balance must be positive or empty";
        public static final String STATUS_SIZE_VALID =  "Status must be under 100 characters long";

        public static final String USERNAME_LENGHT_SIZE =  "Username must be between 2 and 20 characters";
        public static final String USERNAME_REQUIRED =  "Username is required";
        public static final String EMAIL_REQUIRED =  "Email is required";
        public static final String EMAIL_VALID =  "Email must be valid";

        public static final String PASSWORD_REQUIRED =  "Password is required";
        public static final String PASSWORD_SIZE_VALID =  "Password must be atleast 8 characters long";
        public static final String PASSWORD_VALID_FORMAT =  "The password must only include letters and/or numbers";

        public static final String ISBN_REQUIRED =  "Book ISBN is required";
        public static final String HEADLINE_REQUIRED =  "Headline is mandatory";
        public static final String BODY_SIZE_VALID =  "Review must be under 500 characters";
        public static final String HEADLINE_LENGHT_SIZE =  "Headline must be under 60 characters";
        public static final String STARS_REQUIRED =  "Review must have a given star rating";

        public static final String COLLECTION_NAME_REQUIRED =  "Collection name is required";
        public static final String COLLECTION_NAME_SIZE_VALID =  "Collection name must be under 200 characters";

        public static final String FREE_FROM_REQUIRED =  "Free period has to have a start date and time";
        public static final String FREE_UNTIL_REQUIRED =  "Free period should have an end date and time";

        public static final String GENRE_NAME_REQUIRED =  "Genre name is mandatory";
        public static final String GENRE_NAME_SIZE_VALID =  "Genre name must be between 2 and 50 characters";

         public static final String LAST_READ_PAGE_REQUIRED =  "The last read page is required";

        private ValidationMessages() {}
    }


    public static final class LogMessages
    {
        public static final String AUTHOR_ALREADY_EXISTS =  "Authors already exists. Skipping seeding authors.";
        public static final String AUTHOR_SEEDED =  "Authors succesfully seeded!"; 
        public static final String BOOK_ALREADY_EXISTS =  "Books already exists. Skipping seeding books.";
        public static final String BOOK_SEEDED =  "Books successfully seeded!"; 
        public static final String COLLECTION_ALREADY_EXISTS =  "Collections already exists. Skipping seeding collections.";
        public static final String COLLECTION_SEEDED =  "Collections succesfully seeded!"; 
        public static final String GENRE_ALREADY_EXISTS =  "Genres already exists. Skipping seeding genres.";
        public static final String GENRE_SEEDED =  "Genres succesfully seeded!"; 
        public static final String REVIEW_ALREADY_EXISTS =  "Reviews already exists. Skipping seeding reviews.";
        public static final String REVIEW_SEEDED =  "Reviews succesfully seeded!";
        public static final String USER_BOOK_ALREADY_EXISTS =  "User Books already exists. Skipping seeding user_books.";
        public static final String USER_BOOK_SEEDED =  "User Books succesfully seeded!";
        public static final String USER_ALREADY_EXISTS =  "Users already exists. Skipping seeding users.";
        public static final String USER_SEEDED =  "Users successfully seeded!";
        public static final String DECRYPTED_PASSWORD = "The decrypted password for user with the ID %d is %s";
          private LogMessages() {}
    }

      public static final class ValidationRules
    {

        public static final int AUTHOR_NAME_MIN_LENGTH = 2;
        public static final int AUTHOR_NAME_MAX_LENGTH = 20;
        public static final int USERNAME_MIN_LENGTH = 2;
        public static final int USERNAME_MAX_LENGTH = 20;
        public static final int PASSWORD_MIN_LENGTH = 8;
        public static final int STATUS_MAX_LENGTH = 100;
        public static final int BODY_MAX_LENGHT = 500;
        public static final int HEADLINE_MAX_LENGTH = 60;
        public static final int COLLECTION_NAME_MAX_LENGHT = 200;
        public static final int GENRE_NAME_MIN_LENGTH = 2;
        public static final int GENRE_NAME_MAX_LENGTH = 50;
        public static final int ISBN_LENGTH = 13;
        public static final String STARS_MIN = "0.0";
        public static final String STARS_MAX = "5.0";

        public static final String ISBN_PATTERN = "9[78][89]\\d{10}";
        public static final String LANGUAGE_PATTERN = "[A-Za-z]+";
        public static final String PASSWORD_PATTERN = "[a-zA-Z0-9]+";

        public static final int RENTAL_LENGHT = 20;

        public static final int BALANCE_WHOLE = 6;
        public static final int STARS_WHOLE = 1;
        public static final int PRICE_WHOLE = 3;
        public static final int FRACTION = 2;

        private ValidationRules() {}
    }
     public static final class SeederOrder
    {

        public static final int USER = 1;
        public static final int GENRE = 2;
        public static final int AUTHOR = 3;
        public static final int BOOK = 4;
        public static final int REVIEW = 5;
        public static final int COLLECTION = 6;
        public static final int USER_BOOK = 7;
        private SeederOrder() {}
    }

    public static final class Paths
    {
        public static final String AUTHOR_PATH =  "/api/authors"; 
        public static final String AUTHOR_ID =  "/{authorID}";
        public static final String BOOK_PATH =  "/api/books"; 
        public static final String BOOK_ISBN =  "/{isbn}";
        public static final String BOOK_ISBN_FREE_PERIOD =  "/{isbn}/free-period";
        public static final String COLLECTION_PATH =  "/api/collections"; 
        public static final String COLLECTION_ID =  "/{collectionID}";
        public static final String GENRE_PATH =  "/api/genres"; 
        public static final String GENRE_ID =  "/{genreID}";
        public static final String REVIEW_PATH =  "/api/reviews"; 
        public static final String REVIEW_ID =  "/{reviewID}";
        public static final String USER_PATH =  "/api/users";
        public static final String USER_ID = "/{userID}";
        public static final String RENTAL_PATH ="/api/rentals";
        public static final String USERBOOK_PATH ="/api/user-books";
        public static final String NOT_A_NUMBER =  "/G";
        public static final String INVALID_NUMBER_STRING =  "/985";
        public static final String ISBN =  "/9781616086282";

        public static final String COMBINE_PATH(String PATH,String EXTRA) {return  PATH + EXTRA;}
          private Paths() {}
    }

    
    public static final class Misc
    {
        public static final String EMPTY_JSON = """
                                                {}
                                                 """;
        public static final String AUTHOR_ID_STRING =  "authorID";
        public static final String COLLECTION_ID_STRING =  "COLLECTIONID";
        public static final String GENRE_ID_STRING =  "GENREID";
        public static final String REVIEW_ID_STRING =  "REVIEWID";
        public static final String NOT_A_NUMBER_STRING =  "G";
        public static final String INVALID_NUMBER_STRING =  "985"; 
        public static final String ISBN_STRING =  "9781616086282"; 
        public static final String NOT_FOUND = "Not Found";
        public static final Long INVALID_NUMBER =  985L; 

        private Misc() {}
    }
}
