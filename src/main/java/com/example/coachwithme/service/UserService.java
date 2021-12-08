package com.example.coachwithme.service;

import com.example.coachwithme.dto.CreateUserDto;
import com.example.coachwithme.dto.UserDto;
import com.example.coachwithme.exceptions.NotUniqueEmailException;
import com.example.coachwithme.exceptions.UserDoesNotExistException;
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
        assertIfTheEmailIsExisting(createUserDto.getEmail());
        return userMapper.toDto(userRepository.save(userMapper.toEntity(createUserDto)));
    }



    public UserDto showUserProfileInfo(int userId){
        assertIfUserIsExist(userId);
        return userMapper.toDto(userRepository.findById(userId).get());
    }



    //todo
    private void assertIfUserIsExist(int userId){
        if (userRepository.findById(userId).isEmpty()) {
            throw new UserDoesNotExistException("user with " + userId + " is not Existed");
        }

    }

    private void assertIfTheEmailIsExisting(String email){
        if (userRepository.findByEmail(email) != null) {
            throw new NotUniqueEmailException("Email address already exists.");
        }
    }
}
