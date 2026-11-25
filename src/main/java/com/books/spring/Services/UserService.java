package com.books.spring.Services;

import com.books.spring.DTOs.*;
import com.books.spring.Mappers.UserMapper;
import com.books.spring.Misc.BooksJavaConstants.ExceptionMessages;
import com.books.spring.Misc.BooksJavaConstants.LogMessages;
import com.books.spring.Misc.BooksJavaFunctions;
import com.books.spring.Models.User;
import com.books.spring.Repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import com.books.spring.Exception.*;

import java.math.BigDecimal;
import java.util.Base64;
import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserDTO> getAll() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .toList();
    }

    public UserDTO getByUserID(Long userID) { return userMapper.toDto(BooksJavaFunctions.findUser(userRepository,userID)); }

    public UserDTO addBalance(Long userID, BigDecimal amount) {
        User user = BooksJavaFunctions.findUser(userRepository,userID);
        user.setBalance(user.getBalance().add(amount));
        return userMapper.toDto(userRepository.save(user));
    }

    public UserDTO create(RegisterUserDTO dto) {
        if(userRepository.existsByEmail(dto.email())) {
            throw new ConflictException(ExceptionMessages.EMAIL_IS_IN_USE);
        }
        if(userRepository.existsByUsername(dto.username())) {
            throw new ConflictException(ExceptionMessages.USERNAME_IS_IN_USE);
        }

        User user = new User();
        user.setUsername(dto.username());
        user.setEmail(dto.email());
        user.setPassword(dto.password());
        user.setStatus(dto.status());
        return userMapper.toDto(userRepository.save(user));
    }

    public UserDTO update(Long userID, UpdateUserDTO dto) {
        User user = BooksJavaFunctions.findUser(userRepository,userID);

        if(userRepository.existsByEmailAndUSerIDNot(dto.email(), userID)) {
            throw new ConflictException(ExceptionMessages.EMAIL_IS_IN_USE);
        }
        if(userRepository.existsByUsernameAndUserIDNot(dto.username(), userID)) {
            throw new ConflictException(ExceptionMessages.USERNAME_IS_IN_USE);
        }

        user.setUsername(dto.username());
        user.setEmail(dto.email());
        user.setStatus(dto.status());
        return userMapper.toDto(userRepository.save(user));
    }

    public void delete(Long userID) {
        userRepository.delete(BooksJavaFunctions.findUser(userRepository,userID));
    }

    public String decryptPass(Long userID) { 
         String decryptedPass = new String(Base64.getDecoder().decode(BooksJavaFunctions.findUser(userRepository,userID).getPassword())); 
         return LogMessages.DECRYPTED_PASSWORD.formatted(userID,decryptedPass);
        }

}