package com.books.spring.Services;

import com.books.spring.DTOs.RegisterUserDTO;
import com.books.spring.DTOs.UserDTO;
import com.books.spring.Mappers.UserMapper;
import com.books.spring.Models.User;
import com.books.spring.Repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    public UserDTO getByUserID(Long userID) { return userMapper.toDto(findUser(userID)); }

    public UserDTO create(UserDTO dto, RegisterUserDTO registerDto) {
        if(userRepository.existsByEmail(registerDto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email is already in use.");
        }
        if(userRepository.existsByUsername(registerDto.getUsername())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username is already in use.");
        }

        User user = userMapper.toEntity(dto);
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(registerDto.getPassword());
        return userMapper.toDto(userRepository.save(user));
    }

    private User findUser(Long userID) {
        return userRepository.findByUserID(userID)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User " + userID + " could not be found"));
    }

}