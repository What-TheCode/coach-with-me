package com.example.coachwithme.service;

import com.example.coachwithme.dto.coachsession.topic.UpdateTopicExperienceDto;
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
import java.util.List;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class SecurityService {

    private UserRepository userRepository;
    private CoachSessionRepository coachSessionRepository;
    private final static List<SessionState> REQUESTED_STATE_TO_STATE_ACCEPTED_OR_DECLINED_BY_COACH = List.of(SessionState.ACCEPTED, SessionState.DECLINED);
    private final static SessionState REQUESTED_STATE_TO_CANCELED_BY_COACHEE = SessionState.CANCELED_BY_COACHEE;
    private final static SessionState ACCEPTED_STATE_TO_OTHER_STATE_BY_COACH = SessionState.CANCELED_BY_COACH;
    private final static SessionState ACCEPTED_STATE_TO_OTHER_STATE_BY_COACHEE = SessionState.CANCELED_BY_COACHEE;

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

    public boolean isAdmin(int userId) {
        return this.userRepository.getById(userId).getUserRoles().contains(UserRole.ADMIN);
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

    public void assertIfUserIsInTheCoachSessionAndRole(int coachSessionId, UserRole userRole) {
        User user = getLoggedInUser();
        CoachSession coachSession = coachSessionRepository.getById(coachSessionId);

        if (!user.getUserRoles().contains(userRole)) {
            throw new NoPermissionsException("You don't have access to do this");
        }

        if (coachSession.getCoach().getId() != user.getId() && coachSession.getCoachee().getId() != user.getId()) {
            throw new UserIsNotInCoachSessionException("Logged in User is not in this coachsession.");
        }
    }

    public void assertIfUserIsInTheCoachSession(int coachSessionId) {
        User user = getLoggedInUser();
        CoachSession coachSession = coachSessionRepository.getById(coachSessionId);

        if (coachSession.getCoach().getId() != user.getId() && coachSession.getCoachee().getId() != user.getId()) {
            throw new UserIsNotInCoachSessionException("Logged in User is not in this coachsession.");
        }
    }

    public void assertIfSessionStateIsAllowedToChange(int coachSessionId, int userId, SessionState sessionState) {
        SessionState coachSessionState = coachSessionRepository.getById(coachSessionId).getState();
        if (coachSessionState.equals(SessionState.CANCELED_BY_COACH) || coachSessionState.equals(SessionState.CANCELED_BY_COACHEE) || coachSessionState.equals(SessionState.FINISHED) || coachSessionState.equals(SessionState.DECLINED)) {
            throw new WrongSessionStateException("The coach session can not change states after being canceled, finished or declined.");

        }
        if (coachSessionRepository.getById(coachSessionId).getCoach().getId() != userId) {
            if (coachSessionRepository.getById(coachSessionId).getState().equals(SessionState.REQUESTED) && !REQUESTED_STATE_TO_CANCELED_BY_COACHEE.equals(sessionState)) {
                throw new WrongSessionStateException("A Coachee can not change the coach session to this state: " + sessionState);
            }
            if (coachSessionRepository.getById(coachSessionId).getState().equals(SessionState.ACCEPTED) && !ACCEPTED_STATE_TO_OTHER_STATE_BY_COACHEE.equals(sessionState)) {
                throw new WrongSessionStateException("A Coachee can not change the coach session to this state: " + sessionState);
            }

        } else {
            if (coachSessionRepository.getById(coachSessionId).getState().equals(SessionState.REQUESTED) && !REQUESTED_STATE_TO_STATE_ACCEPTED_OR_DECLINED_BY_COACH.contains(sessionState)) {
                throw new WrongSessionStateException("A Coach can not change the coach session to this state: " + sessionState);
            }
            if (coachSessionRepository.getById(coachSessionId).getState().equals(SessionState.ACCEPTED) && !ACCEPTED_STATE_TO_OTHER_STATE_BY_COACH.equals(sessionState)) {
                throw new WrongSessionStateException("A Coach can not change the coach session to this state: " + sessionState);
            }
        }
    }

    public void assertIfListOnlyHasUniqueTopics(List<UpdateTopicExperienceDto> updateTopicExperienceDtos) {
        if (updateTopicExperienceDtos.get(0).getTopicId() == updateTopicExperienceDtos.get(1).getTopicId()) {
            throw new NotUniqueTopicsInTopicExperiencesException("A topic can not be added twice to your teaching list");
        }
    }

}
