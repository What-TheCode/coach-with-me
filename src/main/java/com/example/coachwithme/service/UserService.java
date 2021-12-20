package com.example.coachwithme.service;

import com.example.coachwithme.dto.coachsession.topic.TopicDto;
import com.example.coachwithme.dto.user.*;
import com.example.coachwithme.dto.user.coach.UpdateCoachDto;
import com.example.coachwithme.exceptions.customExceptions.CoachCanNotTeachTopicException;
import com.example.coachwithme.exceptions.customExceptions.UserDoesNotExistException;
import com.example.coachwithme.exceptions.customExceptions.UserIsNotACoachException;
import com.example.coachwithme.mapper.coachssession.topic.TopicMapper;
import com.example.coachwithme.mapper.user.NameMapper;
import com.example.coachwithme.mapper.user.UserMapper;
import com.example.coachwithme.model.user.User;
import com.example.coachwithme.model.user.UserRole;
import com.example.coachwithme.model.user.coach.CoachDetails;
import com.example.coachwithme.repository.TopicRepository;
import com.example.coachwithme.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.List.of;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final NameMapper nameMapper;
    private final PasswordEncoder passwordEncoder;
    private final SecurityService securityService;
    private final TopicMapper topicMapper;
    private final TopicRepository topicRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        } else {
            log.info("User found in the database: {}", email);
        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getUserRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        });
        return user;
    }

    public UserDto registerUser(CreateUserDto createUserDto) {
        securityService.assertIfTheEmailIsExisting(createUserDto.getEmail());
        User user = userMapper.toEntity(createUserDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userMapper.toDto(userRepository.save(user));
    }

    public UserDto showUserProfileInfo(int userId) {
        return userMapper.toDto(userRepository.findById(userId).get());
    }

    public String checkIfEmailExistsInDatabase(String email) {
        return String.valueOf(userRepository.findByEmail(email) != null);
    }

    public String checkIfPictureUrlExistsInDatabase(String pictureUrl) {
        return String.valueOf(userRepository.findByPictureUrl(pictureUrl) != null);
    }

    public UserDto editUserProfileInfo(int userId, UpdateUserDto updateUserDto) {
        securityService.assertIfUserCanModifyProfile(userId);
        User userToUpdate = userRepository.getById(userId);
        assertIfEmailIsValidChange(updateUserDto.getEmail(), userToUpdate.getEmail());
        userToUpdate.setName(nameMapper.toEntity(updateUserDto.getName()));
        userToUpdate.setEmail(updateUserDto.getEmail());
        userToUpdate.setPictureUrl(updateUserDto.getPictureUrl());

        return userMapper.toDto(userRepository.getById(userId));
    }

    public UserDto upGradeToCoach(int userId) {
        securityService.assertIfUserIdMatchesJWTTokenId(userId);
        User userToUpgrade = userRepository.getById(userId);
        userToUpgrade.addUserRole(UserRole.COACH);

        userToUpgrade.setCoachDetails(CoachDetails
                .builder()
                .coachExp(0)
                .build());
        return userMapper.toDto(userToUpgrade);
    }


    public List<CoachDto> getTheCoaches() {
      return userRepository.findAll()
                .stream()
                .filter(role -> role.getUserRoles().contains(UserRole.COACH))

                .filter(coachDetails -> coachDetails.getCoachDetails() != null)
                .map(userMapper::toCoachDto)
                .collect(Collectors.toList());

    }

    public UserDto showCoachProfileInfo(int coachId) {
        assertCheckIfTheUserIsCoach(coachId);
        return userMapper.toDto(userRepository.findById(coachId).get());
    }

    //TODO The returning json gives id 0 back for the topicExperiences -> this is not correct
    // fix that it not always gives a new entry in the database
    // refactor
    public UserDto updateCoach(int userId, UpdateCoachDto updateCoachDto) {
        securityService.assertIfUserCanModifyProfile(userId);

        User coachToUpdate = userRepository.findById(userId)
                .orElseThrow(() -> new UserDoesNotExistException("This user is not found in the data base"));

        coachToUpdate.getCoachDetails().setCoachIntroduction(updateCoachDto.getCoachIntroduction());
        coachToUpdate.getCoachDetails().setCoachAvailability(updateCoachDto.getCoachAvailability());
        userRepository.save(coachToUpdate);
        return showUserProfileInfo(userId);
    }

    private void assertIfEmailIsValidChange(String newEmail, String emailToUpdate) {
        if (newEmail.equals(emailToUpdate)) {
            return;
        }
        System.out.println("passing here");

        securityService.assertIfTheEmailIsExisting(newEmail);
    }

    void assertIfUserIsACoach(int userId) {
        securityService.assertIfUserIdExist(userId);
        if (!userRepository.findById(userId).get().getUserRoles().contains(UserRole.COACH)) {
            throw new UserIsNotACoachException("This user is not a coach");
        }
    }

    public void assertIfCoachCanTeachTopic(int coachId, int topicId) {
        List<Integer> topicIds = userRepository.getById(coachId).getCoachDetails().getCoachExperiences().stream()
                .map(topic -> topic.getTopic().getId())
                .collect(Collectors.toList());
        if (!topicIds.contains(topicId)) {
            throw new CoachCanNotTeachTopicException("This coach can not teach this topic.");
        }
    }

    public List<CoachDto> getTheCoachesByTopic(String topicSelectedName) {

        return userRepository.findAll()
                .stream()
                .filter(role -> role.getUserRoles().contains(UserRole.COACH))
                .filter(coachDetails -> coachDetails.getCoachDetails() != null)


                .filter(topicName->
                        topicName.getCoachDetails().getCoachExperiences()
                                .stream()

                                .allMatch(topic -> topic.getTopic().getName().toLowerCase().contains(topicSelectedName.toLowerCase())))
                .map(userMapper::toCoachDto)
                .collect(Collectors.toList());

    }


    public List<TopicDto> getTopicsNames() {
        return topicRepository.findAll().stream().map(topicMapper::toDto).collect(Collectors.toList());
    }

    private void assertCheckIfTheUserIsCoach(int coachId){
        User user = userRepository.findById(coachId).get();
        if(!user.getUserRoles().contains(UserRole.COACH)){
            throw new CoachCanNotTeachTopicException("This person is not Coach..!");
        }
    }



}

