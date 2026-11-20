package com.books.spring.Services;

import com.books.spring.DTOs.*;
import com.books.spring.Mappers.UserMapper;
import com.books.spring.Misc.BooksJavaConstants.ExceptionMessages;
import com.books.spring.Misc.BooksJavaFunctions;
import com.books.spring.Models.User;
import com.books.spring.Repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.books.spring.Exception.*;

import java.math.BigDecimal;
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
        if(amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException(ExceptionMessages.DEPOSIT_MUST_BE_BIGGER_THAN_ZERO);
        }
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
        return userMapper.toDto(userRepository.save(user));
    }

    public UserDTO update(Long userID, UpdateUserDTO dto) {
        User user = BooksJavaFunctions.findUser(userRepository,userID);

        user.setUsername(dto.username());
        user.setEmail(dto.email());
        user.setStatus(dto.status());
        return userMapper.toDto(userRepository.save(user));
    }

    public void delete(Long userID) {
        userRepository.delete(BooksJavaFunctions.findUser(userRepository,userID));
    }

}