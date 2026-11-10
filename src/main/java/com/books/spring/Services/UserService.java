package com.books.spring.Services;

import com.books.spring.DTOs.RegisterUserDTO;
import com.books.spring.DTOs.UpdateUserDTO;
import com.books.spring.DTOs.UserDTO;
import com.books.spring.Mappers.UserMapper;
import com.books.spring.Models.Author;
import com.books.spring.Models.User;
import com.books.spring.Repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
                .filter(user -> !user.isDeleted())
                .map(userMapper::toDto)
                .toList();
    }

    public UserDTO getByUserID(Long userID) { return userMapper.toDto(findUser(userID)); }

    public UserDTO addBalance(Long userID, BigDecimal amount) {
        if(amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Deposit amount must be greater than zero");
        }
        User user = findUser(userID);
        user.setBalance(user.getBalance().add(amount));
        return userMapper.toDto(userRepository.save(user));
    }

    public UserDTO create(RegisterUserDTO dto) {
        if(userRepository.existsByEmail(dto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email is already in use.");
        }
        if(userRepository.existsByUsername(dto.getUsername())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username is already in use.");
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        return userMapper.toDto(userRepository.save(user));
    }

    public UserDTO update(Long userID, UpdateUserDTO dto) {
        User user = findUser(userID);

        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setStatus(dto.getStatus());
        return userMapper.toDto(userRepository.save(user));
    }

    public void delete(Long userID) {
        User user = findUser(userID);
        user.setDeleted(true);
        userRepository.save(user);
    }

    @Deprecated
    public void hardDelete(Long userID) {
        userRepository.delete(findUser(userID));
    }

    private User findUser(Long userID) {
        return userRepository.findByUserID(userID)
                .filter(user -> !user.isDeleted())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User " + userID + " could not be found"));
    }

}