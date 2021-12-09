package com.example.coachwithme.service;

import com.example.coachwithme.dto.CoachDetailsCreateDto;
import com.example.coachwithme.dto.CreateUserDto;
import com.example.coachwithme.dto.UpdateUserDto;
import com.example.coachwithme.dto.UserDto;
import com.example.coachwithme.exceptions.NotUniqueEmailException;
import com.example.coachwithme.exceptions.UserDoesNotExistException;
import com.example.coachwithme.mapper.CoachDetailMapper;
import com.example.coachwithme.mapper.UserMapper;
import com.example.coachwithme.model.user.User;
import com.example.coachwithme.model.user.UserRole;
import com.example.coachwithme.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserService {
    private static final UserRole COACH = UserRole.COACH;

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final CoachDetailMapper coachDetailMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper, CoachDetailMapper coachDetailMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.coachDetailMapper = coachDetailMapper;
    }

    public UserDto registerUser(CreateUserDto createUserDto){
        assertIfTheEmailIsExisting(createUserDto.getEmail());
        return userMapper.toDto(userRepository.save(userMapper.toEntity(createUserDto)));
    }



    public UserDto showUserProfileInfo(int userId){
        assertIfUserIsExist(userId);
        return userMapper.toDto(userRepository.findById(userId).get());
    }


    public UserDto editUserProfileInfo(int userId, UpdateUserDto updateUserDto) {
        assertIfUserIsExist(userId);
        User userToUpdate = userRepository.getById(userId);

        userToUpdate.getName().setFirstName(updateUserDto.getName().getFirstName());
        userToUpdate.getName().setLastName(updateUserDto.getName().getLastName());
        userToUpdate.setEmail(updateUserDto.getEmail());
        userToUpdate.setPictureUrl(updateUserDto.getPictureUrl());

        return userMapper.toDto(userToUpdate);
    }


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

    public UserDto upGradeToCoach(CoachDetailsCreateDto coachDetailsCreateDto, int userId) {
        assertIfUserIsExist(userId);
        User userToUpgrade = userRepository.getById(userId);
        userToUpgrade.addUserRole(COACH);
        userToUpgrade.setCoachDetails(coachDetailMapper.toEntity(coachDetailsCreateDto));
        return userMapper.toDto(userToUpgrade);
    }
}
