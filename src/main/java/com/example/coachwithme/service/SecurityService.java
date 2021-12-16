package com.example.coachwithme.service;

import com.example.coachwithme.exceptions.customExceptions.*;
import com.example.coachwithme.model.coachSession.CoachSession;
import com.example.coachwithme.model.coachSession.SessionState;
import com.example.coachwithme.model.user.User;
import com.example.coachwithme.model.user.UserRole;
import com.example.coachwithme.repository.CoachSessionRepository;
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
    private CoachSessionRepository coachSessionRepository;

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
            throw new UserDoesNotExistException("user with id " + userId + " does not exist");
        }
    }

    public void assertIfTheEmailIsExisting(String email) {
        if (isEmailFoundInTheDatabase(email)) {
            throw new NotUniqueEmailException("Email address already exists.");
        }
    }

    boolean isEmailFoundInTheDatabase(String email) {
        return userRepository.findByEmail(email) != null;
    }


    public void assertIfUserIsInTheCoachSession(int coachSessionId, int userId) {
        CoachSession coachSession = coachSessionRepository.getById(coachSessionId);
        if (coachSession.getCoach().getId() != userId && coachSession.getCoachee().getId() != userId){
            throw new UserIsNotInCoachSessionException("User is not in this coachsession and therefore can not change the state of it.");
        }

    }

    public void assertIfCoachSessionExistAndUserIsIntCoachSession(int coachSessionId) {
        if (coachSessionRepository.findById(coachSessionId).isEmpty()){
            throw new CoachSessionDoesNotExistException("This coachsession does not exist.");
        }
    }

    public void assertIfSessionStateIsAllowedToChange(int coachSessionId, int userId, SessionState sessionState) {
        if(!userRepository.getById(userId).getUserRoles().contains(UserRole.COACH)){

        }
    }
}
