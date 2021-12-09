package com.example.coachwithme.service;

import com.example.coachwithme.dto.*;
import com.example.coachwithme.exceptions.NotUniqueEmailException;
import com.example.coachwithme.exceptions.UserDoesNotExistException;
import com.example.coachwithme.mapper.CoachDetailMapper;
import com.example.coachwithme.mapper.NameMapper;
import com.example.coachwithme.mapper.TopicExperienceMapper;
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
    private final NameMapper nameMapper;


    public UserService(UserRepository userRepository, UserMapper userMapper, CoachDetailMapper coachDetailMapper, NameMapper nameMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.coachDetailMapper = coachDetailMapper;
        this.nameMapper= nameMapper;

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
        assertIfEmailIsValidChange(updateUserDto.getEmail(), userToUpdate.getEmail());
        userToUpdate.setName(nameMapper.toEntity(updateUserDto.getName()));
        userToUpdate.setEmail(updateUserDto.getEmail());
        userToUpdate.setPictureUrl(updateUserDto.getPictureUrl());

        return userMapper.toDto(userRepository.getById(userId));
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

    //TODO The returning json gives id 0 back for the topicExperiences -> this is not correct
    // fix that it not always gives a new entry in the database
    // refactor
    public UserDto updateCoach(int userId, UpdateCoachDto updateCoachDto){
        assertIfUserIsExist(userId);
        User coachToUpdate = userRepository.getById(userId);
        assertIfEmailIsValidChange(updateCoachDto.getEmail(), coachToUpdate.getEmail());
        coachToUpdate.setName(nameMapper.toEntity(updateCoachDto.getName()));
        coachToUpdate.setEmail(updateCoachDto.getEmail());
        coachToUpdate.setPictureUrl(updateCoachDto.getPictureUrl());
        coachToUpdate.setCoachDetails(coachDetailMapper.toEntity(updateCoachDto.getCoachDetailsCreateDto()));

        return showUserProfileInfo(userId);
    }

    private void assertIfEmailIsValidChange(String newEmail, String emailToUpdate) {
        if(newEmail.equals(emailToUpdate)){
            return;
        }

        assertIfTheEmailIsExisting(newEmail);

    }
}
