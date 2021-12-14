package com.example.coachwithme.service;

import com.example.coachwithme.exceptions.customExceptions.NotUniqueEmailException;
import com.example.coachwithme.exceptions.customExceptions.UserDoesNotExistException;
import com.example.coachwithme.model.user.User;
import com.example.coachwithme.model.user.UserRole;
import com.example.coachwithme.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not allowed to view this");
        }
    }

    public void assertIfUserCanModifyProfile(int userId) {
        assertIfUserIdExist(userId);

        String loggedInEmail = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByEmail(loggedInEmail);

        if (user.getId() != userId && !user.getUserRoles().contains(UserRole.ADMIN)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not allowed to view this");
        }
    }

    public void assertIfUserIdExist(int userId) {
        if (userRepository.findById(userId).isEmpty()) {
            throw new UserDoesNotExistException("user with " + userId + " is not Existed");
        }
    }

    public void assertIfTheEmailIsExisting(String email) {
        if (isaBoolean(email)) {
            throw new NotUniqueEmailException("Email address already exists.");
        }
    }


    boolean isaBoolean(String email) {
        return userRepository.findByEmail(email) != null;
    }

}
