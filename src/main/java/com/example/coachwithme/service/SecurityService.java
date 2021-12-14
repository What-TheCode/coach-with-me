package com.example.coachwithme.service;

import com.example.coachwithme.exceptions.customExceptions.NoPermissionsException;
import com.example.coachwithme.exceptions.customExceptions.NotUniqueEmailException;
import com.example.coachwithme.exceptions.customExceptions.UserDoesNotExistException;
import com.example.coachwithme.model.user.User;
import com.example.coachwithme.model.user.UserRole;
import com.example.coachwithme.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class SecurityService {

    private UserRepository userRepository;

    public void assertIfUserIdMatchesJWTTokenId(int userId) {
        assertIfUserIdExist(userId);

        String loggedInEmail = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByEmail(loggedInEmail);

        if (user.getId() != userId) {
            throw new NoPermissionsException("You don't have access to do this");
        }
    }

    public void assertIfUserIsAdmin() {
        User user = getLoggedInUser();

        if (!user.getUserRoles().contains(UserRole.ADMIN)) {
            throw new NoPermissionsException("You don't have access to do this");
        }
    }

    public void assertIfUserCanModifyProfile(int userId) {
        assertIfUserIdExist(userId);
        User user = getLoggedInUser();

        if (user.getId() != userId && !user.getUserRoles().contains(UserRole.ADMIN)) {
            throw new NoPermissionsException("You don't have access to do this");
        }
    }

    public User getLoggedInUser() {
        String loggedInEmail = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByEmail(loggedInEmail);
    }

    public void assertIfUserIdExist(int userId) {
        if (userRepository.findById(userId).isEmpty()) {
            throw new UserDoesNotExistException("user with " + userId + " is not Existed");
        }
    }

    public void assertIfTheEmailIsExisting(String email) {
        if (userRepository.findByEmail(email) != null) {
            throw new NotUniqueEmailException("Email address already exists.");
        }
    }

}
