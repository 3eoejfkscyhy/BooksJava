package com.books.spring.Services;

import com.books.spring.DTOs.*;
import com.books.spring.Mappers.UserMapper;
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
                .filter(user -> !user.getIsDeleted())
                .map(userMapper::toDto)
                .toList();
    }

    public UserDTO getByUserID(Long userID) { return userMapper.toDto(findUser(userID)); }

    public UserDTO addBalance(Long userID, BigDecimal amount) {
        if(amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException( "Deposit amount must be greater than zero");
        }
        User user = findUser(userID);
        user.setBalance(user.getBalance().add(amount));
        return userMapper.toDto(userRepository.save(user));
    }

    public UserDTO create(RegisterUserDTO dto) {
        if(userRepository.existsByEmail(dto.email())) {
            throw new ConflictException("Email is already in use.");
        }
        if(userRepository.existsByUsername(dto.username())) {
            throw new ConflictException("Username is already in use.");
        }

        User user = new User();
        user.setUsername(dto.username());
        user.setEmail(dto.email());
        user.setPassword(dto.password());
        return userMapper.toDto(userRepository.save(user));
    }

    public UserDTO update(Long userID, UpdateUserDTO dto) {
        User user = findUser(userID);

        user.setUsername(dto.username());
        user.setEmail(dto.email());
        user.setStatus(dto.status());
        return userMapper.toDto(userRepository.save(user));
    }

    public void delete(Long userID) {
        User user = findUser(userID);
        user.setIsDeleted(true);
        userRepository.save(user);
    }

    @Deprecated
    public void hardDelete(Long userID) {
        userRepository.delete(findUser(userID));
    }

    private User findUser(Long userID) {
        return userRepository.findByUserID(userID)
                .filter(user -> !user.getIsDeleted())
                .orElseThrow(() -> new NotFoundException( "User " + userID + " could not be found"));
    }

}