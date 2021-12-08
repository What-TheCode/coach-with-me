package com.example.coachwithme.service;

import com.example.coachwithme.dto.CreateUserDto;
import com.example.coachwithme.dto.UserDto;
import com.example.coachwithme.exceptions.ControllerAdvisor;
import com.example.coachwithme.exceptions.NotUniqueEmailException;
import com.example.coachwithme.mapper.UserMapper;
import com.example.coachwithme.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserDto registerUser(CreateUserDto createUserDto){
        return userMapper.toDto(userRepository.save(userMapper.toEntity(createUserDto)));
    }
}
