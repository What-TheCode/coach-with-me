package com.example.coachwithme.service;

import com.example.coachwithme.dto.coachsession.CoachSessionDto;
import com.example.coachwithme.dto.coachsession.CreateCoachSessionDto;
import com.example.coachwithme.dto.coachsession.feedback.CoachFeedbackDto;
import com.example.coachwithme.dto.coachsession.feedback.CoacheeFeedbackDto;
import com.example.coachwithme.dto.coachsession.feedback.CreateCoachFeedbackDto;
import com.example.coachwithme.dto.coachsession.feedback.CreateCoacheeFeedbackDto;
import com.example.coachwithme.exceptions.customExceptions.CoachSessionDoesNotExistException;
import com.example.coachwithme.exceptions.customExceptions.FeedbackAlreadyGivenException;
import com.example.coachwithme.exceptions.customExceptions.WrongSessionStateException;
import com.example.coachwithme.mapper.coachssession.CoachSessionMapper;
import com.example.coachwithme.mapper.coachssession.feedback.CoachFeedbackMapper;
import com.example.coachwithme.mapper.coachssession.feedback.CoacheeFeedbackMapper;
import com.example.coachwithme.model.coachSession.CoachSession;
import com.example.coachwithme.model.coachSession.SessionState;
import com.example.coachwithme.model.coachSession.feedback.CoachFeedback;
import com.example.coachwithme.model.coachSession.feedback.CoacheeFeedback;
import com.example.coachwithme.model.coachSession.feedback.SessionFeedback;
import com.example.coachwithme.model.user.UserRole;
import com.example.coachwithme.repository.CoachSessionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class CoachSessionService {
    public static final int REWARD_AMOUNT_OF_EXP_AFTER_COACH_SESSION = 10;
    private final CoachSessionMapper coachSessionMapper;
    private final UserService userService;
    private final CoachSessionRepository coachSessionRepository;
    private final SecurityService securityService;
    private final CoacheeFeedbackMapper coacheeFeedbackMapper;
    private final CoachFeedbackMapper coachFeedbackMapper;


    public CoachSessionDto registerACoachSession(CreateCoachSessionDto createCoachSessionDto) {
//        sessionValidation(createCoachSessionDto);
        this.securityService.assertIfUserIdMatchesJWTTokenId(createCoachSessionDto.getCoacheeId());

        CoachSession newCoachSession = coachSessionMapper.toEntity(createCoachSessionDto);
        this.coachSessionRepository.save(newCoachSession);
        return coachSessionMapper.toDtos(newCoachSession);
    }

    public List<CoachSessionDto> getCoachSessionsAsCoachee(int coacheeId) {
        this.securityService.assertIfUserIdMatchesJWTTokenId(coacheeId);

        return this.coachSessionMapper.toDtos(
                this.coachSessionRepository.findCoachSessionsByCoacheeId(coacheeId));
    }

    public List<CoachSessionDto> getCoachSessionsAsCoach(int coachId) {
        this.securityService.assertIfUserIdMatchesJWTTokenId(coachId);

        return this.coachSessionMapper.toDtos(this.coachSessionRepository.findCoachSessionsByCoachId(coachId));
    }

    public List<CoachSessionDto> getAllCoachingSessions() {
        return this.coachSessionMapper.toDtos(this.coachSessionRepository.findAll());
    }

    public List<CoachSession> getAllAcceptedCoachSessions() {
        return coachSessionRepository.findCoachSessionByState(SessionState.ACCEPTED);
    }

    public List<CoachSession> getAllRequestedCoachSessions() {
        return coachSessionRepository.findCoachSessionByState(SessionState.REQUESTED);
    }

    public CoachSessionDto updateCoachSession(int coachSessionId, int userId, SessionState sessionState) {
        CoachSessionStateValidation(coachSessionId, userId, sessionState);
        CoachSession coachSessionToUpdate = coachSessionRepository.findById(coachSessionId).get();
        coachSessionToUpdate.setState(sessionState);
        return coachSessionMapper.toDtos(coachSessionToUpdate);
    }

    public CoachFeedbackDto addCoachFeedback(int coachSessionId, CreateCoachFeedbackDto createCoachFeedbackDto) {
        assertCoachSessionsIdExists(coachSessionId);
        assertCoachSessionsStateIsWaitingForFeedback(coachSessionId);
        securityService.assertIfUserIsInTheCoachSessionAndRole(coachSessionId, UserRole.COACH);

        CoachSession coachSession = coachSessionRepository.findById(coachSessionId).get();

        if (coachSession.getFeedback().getCoachFeedback() != null) {
            throw new FeedbackAlreadyGivenException("You can only give feedback once.");
        }

        CoachFeedback coachFeedback = coachFeedbackMapper.toEntity(createCoachFeedbackDto);
        coachSession.getFeedback().setCoachFeedback(coachFeedback);

        checkIfBothFeedBackAreGiven(coachSession);

        return coachFeedbackMapper.toDto(coachFeedback);
    }

    public CoacheeFeedbackDto addCoacheeFeedback(int coachSessionId, CreateCoacheeFeedbackDto createCoacheeFeedbackDto) {
        assertCoachSessionsIdExists(coachSessionId);
        assertCoachSessionsStateIsWaitingForFeedback(coachSessionId);
        securityService.assertIfUserIsInTheCoachSessionAndRole(coachSessionId, UserRole.COACHEE);

        CoachSession coachSession = coachSessionRepository.findById(coachSessionId).get();

        if (coachSession.getFeedback().getCoacheeFeedback() != null) {
            throw new FeedbackAlreadyGivenException("You can only give feedback once.");
        }

        CoacheeFeedback coacheeFeedback = coacheeFeedbackMapper.toEntity(createCoacheeFeedbackDto);
        coachSession.getFeedback().setCoacheeFeedback(coacheeFeedback);

        checkIfBothFeedBackAreGiven(coachSession);

        return coacheeFeedbackMapper.toDto(coacheeFeedback);
    }

    public void checkForOverdueRequestedCoachSessions() {
        log.info("scheduled service - Checking for requested coach Sessions.");
        for (CoachSession coachSession : getAllRequestedCoachSessions()) {
            if (isCoachSessionsOverdue(coachSession)) {
                log.info("Coach session State changed to CANCELED BY SYSTEM because Date is overdue with Id {} ", coachSession.getId());
                coachSession.setState(SessionState.CANCELED_BY_SYSTEM);
            }
        }
    }

    public void checkForOverdueAcceptedCoachSessions() {
        log.info("scheduled service - Checking for overdue accepted coach Sessions.");
        for (CoachSession coachSession : getAllAcceptedCoachSessions()) {
            if (isCoachSessionsOverdue(coachSession)) {
                log.info("Coach session State changed to DONE WAITING FEEDBACK because Date is overdue with Id {} ", coachSession.getId());
                coachSession.setState(SessionState.DONE_WAITING_FEEDBACK);
                coachSession.setFeedback(new SessionFeedback());
            }
        }
    }

    private boolean isCoachSessionsOverdue(CoachSession coachSession) {
        //Check if Sessions Date is before our current date
        if (coachSession.getDate().isBefore(LocalDate.now())) {
            return true;
        }

        //Check if Sessions Date is our current date and time is before out current time
        if (coachSession.getDate().isEqual(LocalDate.now())) {
            if (coachSession.getTime().isBefore(LocalTime.now())) {
                return true;
            }
        }
        return false;
    }

    private void assertCoachSessionsIdExists(int coachSessionsId) {
        if (coachSessionRepository.findById(coachSessionsId).isEmpty()) {
            throw new CoachSessionDoesNotExistException("Coach session with id: + " + coachSessionsId + " does not exist");
        }
    }

    private void assertCoachSessionsStateIsWaitingForFeedback(int coachSessionsId) {
        CoachSession coachSession = coachSessionRepository.findById(coachSessionsId).get();
        if (coachSession.getState() != SessionState.DONE_WAITING_FEEDBACK) {
            throw new WrongSessionStateException("You can't give feedback on this session.");
        }
    }

    private void checkIfBothFeedBackAreGiven(CoachSession coachSession) {
        if (coachSession.getFeedback().getCoachFeedback() != null && coachSession.getFeedback().getCoacheeFeedback() != null) {
            log.info("Both feedbacks are given for Coach session Id:" + coachSession.getId() + " - Given 10 XP point to the Coach");
            coachSession.getCoach().getCoachDetails().addExp(REWARD_AMOUNT_OF_EXP_AFTER_COACH_SESSION);
            coachSession.setState(SessionState.FINISHED);
        }
    }

    private void sessionValidation(CreateCoachSessionDto createCoachSessionDto) {
        userService.assertIfUserIsACoach(createCoachSessionDto.getCoachId());
        userService.assertIfCoachCanTeachTopic(createCoachSessionDto.getCoachId(), createCoachSessionDto.getTopicId());
    }

    private void CoachSessionStateValidation(int coachSessionId, int userId, SessionState sessionState) {
        if (!this.securityService.isAdmin(userId)) {
            this.securityService.assertIfUserIdMatchesJWTTokenId(userId);
            this.securityService.assertIfUserIsInTheCoachSession(coachSessionId);
            this.securityService.assertIfSessionStateIsAllowedToChange(coachSessionId, userId, sessionState);
        }
    }

}
