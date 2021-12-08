package com.example.coachwithme.service;

import com.example.coachwithme.dto.UserDto;
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

    public UserDto registerUser(UserDto userDto){
        return userMapper.toDto(userRepository.save(userMapper.toEntity(userDto)));
    }
}
