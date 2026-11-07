package com.books.spring.Controllers;

import com.books.spring.DTOs.RegisterUserDTO;
import com.books.spring.DTOs.UpdateUserDTO;
import com.books.spring.DTOs.UserDTO;
import com.books.spring.Services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<UserDTO> getAll() {
        return userService.getAll();
    }

    @GetMapping("/{userID}")
    public UserDTO getByIsbn(@PathVariable Long userID) {
        return userService.getByUserID(userID);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO create(@Valid @RequestBody RegisterUserDTO dto) {
        return userService.create(dto);
    }

    @PutMapping("/{userID}")
    public UserDTO update(@PathVariable Long userID, @Valid @RequestBody UpdateUserDTO dto) {
        return userService.update(userID, dto);
    }

    @DeleteMapping("/{userID}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long userID) {
        userService.delete(userID);
    }

    @Deprecated
    @DeleteMapping("/hard-delete/{userID}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void hardDelete(@PathVariable Long userID) {
        userService.hardDelete(userID);
    }
}