package com.example.coachwithme.service;

import com.example.coachwithme.dto.coachsession.topic.TopicDto;
import com.example.coachwithme.dto.coachsession.topic.UpdateTopicExperienceDto;
import com.example.coachwithme.dto.user.*;
import com.example.coachwithme.dto.user.coach.UpdateCoachDto;
import com.example.coachwithme.exceptions.customExceptions.CoachCanNotTeachTopicException;
import com.example.coachwithme.exceptions.customExceptions.UserIsNotACoachException;
import com.example.coachwithme.mapper.coachssession.topic.TopicExperienceMapper;
import com.example.coachwithme.mapper.coachssession.topic.TopicMapper;
import com.example.coachwithme.mapper.user.NameMapper;
import com.example.coachwithme.mapper.user.UserMapper;
import com.example.coachwithme.model.user.User;
import com.example.coachwithme.model.user.UserRole;
import com.example.coachwithme.model.user.coach.CoachDetails;
import com.example.coachwithme.repository.TopicExperienceRepository;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.List.of;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class UserService implements UserDetailsService {

    public static final String DEFAULT_PROFILE_PICTURE = "https://i.imgur.com/5MzuCZy.png";
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final NameMapper nameMapper;
    private final TopicExperienceMapper topicExperienceMapper;
    private final PasswordEncoder passwordEncoder;
    private final SecurityService securityService;
    private final TopicMapper topicMapper;
    private final TopicRepository topicRepository;
    private final TopicExperienceRepository topicExperienceRepository;

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
        assertIfPictureIsValid(createUserDto);

        User user = userMapper.toEntity(createUserDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userMapper.toDto(userRepository.save(user));
    }

    public UserDto showUserProfileInfo(int userId) {
//        securityService.assertIfUserIdMatchesJWTTokenId(userId);
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

    public List<UserDto> getAllUsers() {
        return userMapper.toDtos(userRepository.findAll());
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
                .filter(overview -> overview.getCoachDetails().getCoachExperiences().size() != 0)
                .map(userMapper::toCoachDto)
                .collect(Collectors.toList());

    }

    public UserDto showCoachProfileInfo(int coachId) {
        assertCheckIfTheUserIsCoach(coachId);
        return userMapper.toDto(userRepository.findById(coachId).get());
    }

    public UserDto updateCoach(int userId, UpdateCoachDto updateCoachDto) {
        securityService.assertIfUserIdExist(userId);
        User coachToUpdate = userRepository.findById(userId).get();
        coachToUpdate.getCoachDetails().setCoachIntroduction(updateCoachDto.getCoachIntroduction());
        coachToUpdate.getCoachDetails().setCoachAvailability(updateCoachDto.getCoachAvailability());
        return showUserProfileInfo(userId);
    }

    public UserDto updateCoachTopicExperience(int userId, List<UpdateTopicExperienceDto> updateTopicExperienceDtos) {
        securityService.assertIfUserIdExist(userId);
        securityService.assertIfListOnlyHasUniqueTopics(updateTopicExperienceDtos);
        User coachToUpdate = userRepository.findById(userId).get();
        coachToUpdate.getCoachDetails().setCoachExperiences(topicExperienceMapper.fromUpdateDtoToEntities(updateTopicExperienceDtos));
        return showUserProfileInfo(userId);
    }

    private void assertIfEmailIsValidChange(String newEmail, String emailToUpdate) {
        if (newEmail.equals(emailToUpdate)) {
            return;
        }
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
                .filter(topicexperiences -> topicexperiences.getCoachDetails().getCoachExperiences().size() != 0)
                .filter(topicName ->
                        topicName.getCoachDetails().getCoachExperiences()
                                .stream()
                                .anyMatch(topic -> topic.getTopic().getName().toLowerCase().contains(topicSelectedName.toLowerCase())))
                .map(userMapper::toCoachDto)
                .collect(Collectors.toList());

    }


    public List<TopicDto> getTopicsNames() {
        return topicRepository.findAll().stream().map(topicMapper::toDto).collect(Collectors.toList());
    }

    private void assertCheckIfTheUserIsCoach(int coachId) {
        User user = userRepository.findById(coachId).get();
        if (!user.getUserRoles().contains(UserRole.COACH)) {
            throw new CoachCanNotTeachTopicException("This person is not Coach..!");
        }
    }

    private void assertIfPictureIsValid(CreateUserDto createUserDto) {

        if (createUserDto.getPictureUrl() == null || createUserDto.getPictureUrl().isBlank()) {
            createUserDto.setPictureUrl(DEFAULT_PROFILE_PICTURE);
            return;
        }

        if (!createUserDto.getPictureUrl().contains(".jpg") && !createUserDto.getPictureUrl().contains(".jepg") && !createUserDto.getPictureUrl().contains(".png")) {
            createUserDto.setPictureUrl(DEFAULT_PROFILE_PICTURE);
        }
    }

    public List<TopicDto> getTopicsNamesByCoachId(int coachId) {

        List<Integer> topicIds = userRepository.getById(coachId).getCoachDetails().getCoachExperiences().stream()
                .map(topic -> topic.getTopic().getId())
                .collect(Collectors.toList());


        List<TopicDto> topicDtoList = topicRepository.findAll()
                .stream()
                .map(topicMapper::toDto).collect(Collectors.toList());

        List<TopicDto> coachTopicListDto = new ArrayList<>();

        for (TopicDto topicDto : topicDtoList) {
            for (Integer topicId : topicIds) {
                if (topicDto.getId() == topicId) {
                    coachTopicListDto.add(topicDto);
                }
            }

        }
        return coachTopicListDto;

    }


}

