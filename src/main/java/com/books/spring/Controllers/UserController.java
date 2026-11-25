package com.books.spring.Controllers;

import com.books.spring.DTOs.*;
import com.books.spring.Exception.BadRequestException;
import com.books.spring.Services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.books.spring.Misc.BooksJavaConstants.ExceptionMessages;
import com.books.spring.Misc.BooksJavaConstants.Paths;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping(Paths.USER_PATH)
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<UserDTO> getAll() {
        return userService.getAll();
    }

    @GetMapping(Paths.USER_ID)
    public UserDTO getByIsbn(@PathVariable Long userID) {
        return userService.getByUserID(userID);
    }

    @GetMapping("/{userID}/decrypt-password")
    public String decryptPass(@PathVariable Long userID) {
        return userService.decryptPass(userID);
    }

    @PostMapping("/{userID}/balance")
    public UserDTO addBalance(@PathVariable Long userID, @RequestParam BigDecimal amount) {
                if(amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException(ExceptionMessages.DEPOSIT_MUST_BE_BIGGER_THAN_ZERO);
        }
        return userService.addBalance(userID, amount);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO create(@Valid @RequestBody RegisterUserDTO dto) {
        return userService.create(dto);
    }

    @PutMapping(Paths.USER_ID)
    public UserDTO update(@PathVariable Long userID, @Valid @RequestBody UpdateUserDTO dto) {
        return userService.update(userID, dto);
    }

    @DeleteMapping(Paths.USER_ID)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long userID) {
        userService.delete(userID);
    }
    }
