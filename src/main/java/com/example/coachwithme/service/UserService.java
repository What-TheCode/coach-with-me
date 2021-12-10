package com.example.coachwithme.service;

import com.example.coachwithme.dto.CreateUserDto;
import com.example.coachwithme.dto.UpdateCoachDto;
import com.example.coachwithme.dto.UpdateUserDto;
import com.example.coachwithme.dto.UserDto;
import com.example.coachwithme.exceptions.NotUniqueEmailException;
import com.example.coachwithme.exceptions.UserDoesNotExistException;
import com.example.coachwithme.mapper.CoachDetailMapper;
import com.example.coachwithme.mapper.NameMapper;
import com.example.coachwithme.mapper.UserMapper;
import com.example.coachwithme.model.user.User;
import com.example.coachwithme.model.user.UserRole;
import com.example.coachwithme.model.user.coach.CoachDetails;
import com.example.coachwithme.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;

@Service
@Transactional
@Slf4j
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final CoachDetailMapper coachDetailMapper;
    private final NameMapper nameMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserMapper userMapper, CoachDetailMapper coachDetailMapper, NameMapper nameMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.coachDetailMapper = coachDetailMapper;
        this.nameMapper = nameMapper;
        this.passwordEncoder = passwordEncoder;
    }

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
        assertIfTheEmailIsExisting(createUserDto.getEmail());
        User user = userMapper.toEntity(createUserDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userMapper.toDto(userRepository.save(user));
    }

    public UserDto showUserProfileInfo(int userId) {
        assertIfUserCanViewProfile(userId);
        return userMapper.toDto(userRepository.findById(userId).get());
    }

    public UserDto editUserProfileInfo(int userId, UpdateUserDto updateUserDto) {
        assertIfUserCanModifyProfile(userId);

        User userToUpdate = userRepository.getById(userId);
        assertIfEmailIsValidChange(updateUserDto.getEmail(), userToUpdate.getEmail());
        userToUpdate.setName(nameMapper.toEntity(updateUserDto.getName()));
        userToUpdate.setEmail(updateUserDto.getEmail());
        userToUpdate.setPictureUrl(updateUserDto.getPictureUrl());

        return userMapper.toDto(userRepository.getById(userId));
    }

    private void assertIfUserIdExist(int userId) {
        if (userRepository.findById(userId).isEmpty()) {
            throw new UserDoesNotExistException("user with " + userId + " is not Existed");
        }
    }

    private void assertIfUserCanViewProfile(int userId) {
        assertIfUserIdExist(userId);

        String loggedInEmail = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByEmail(loggedInEmail);

        if (user.getId() != userId) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not allowed to view this");
        }
    }

    private void assertIfUserCanModifyProfile(int userId) {
        assertIfUserIdExist(userId);

        String loggedInEmail = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByEmail(loggedInEmail);

        if (user.getId() != userId && !user.getUserRoles().contains(UserRole.ADMIN)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not allowed to view this");
        }
    }

    private void assertIfTheEmailIsExisting(String email) {
        if (userRepository.findByEmail(email) != null) {
            throw new NotUniqueEmailException("Email address already exists.");
        }
    }

    public UserDto upGradeToCoach(int userId) {
        assertIfUserIdExist(userId);
        User userToUpgrade = userRepository.getById(userId);
        userToUpgrade.addUserRole(UserRole.COACH);

        userToUpgrade.setCoachDetails(CoachDetails
                .builder()
                .coachExp(0)
                .build());
        return userMapper.toDto(userToUpgrade);
    }

    //TODO The returning json gives id 0 back for the topicExperiences -> this is not correct
    // fix that it not always gives a new entry in the database
    // refactor
    public UserDto updateCoach(int userId, UpdateCoachDto updateCoachDto) {
        assertIfUserIdExist(userId);
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

        assertIfTheEmailIsExisting(newEmail);
    }
}

