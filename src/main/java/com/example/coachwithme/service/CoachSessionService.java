package com.example.coachwithme.service;

import com.example.coachwithme.dto.coachsession.CoachSessionDto;
import com.example.coachwithme.dto.coachsession.CreateCoachSessionDto;
import com.example.coachwithme.mapper.coachssession.CoachSessionMapper;
import com.example.coachwithme.model.coachSession.CoachSession;
import com.example.coachwithme.model.coachSession.SessionState;
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
    private final CoachSessionMapper coachSessionMapper;
    private final UserService userService;
    private final CoachSessionRepository coachSessionRepository;
    private final SecurityService securityService;


    public CoachSessionDto registerACoachSession(CreateCoachSessionDto createCoachSessionDto) {
//        sessionValidation(createCoachSessionDto);
        this.securityService.assertIfUserIdMatchesJWTTokenId(createCoachSessionDto.getCoacheeId());

        CoachSession newCoachSession = coachSessionMapper.toEntity(createCoachSessionDto);
        this.coachSessionRepository.save(newCoachSession);
        return coachSessionMapper.toDto(newCoachSession);
    }

    public List<CoachSessionDto> getCoachSessionsAsCoachee(int coacheeId) {
        this.securityService.assertIfUserIdMatchesJWTTokenId(coacheeId);

        return this.coachSessionMapper.toDto(
                this.coachSessionRepository.findCoachSessionsByCoacheeId(coacheeId));
    }

    public List<CoachSessionDto> getCoachSessionsAsCoach(int coachId) {
        this.securityService.assertIfUserIdMatchesJWTTokenId(coachId);

        return this.coachSessionMapper.toDto(
                this.coachSessionRepository.findCoachSessionsByCoachId(coachId));
    }

    public List<CoachSession> getAllAcceptedCoachSessions() {
        return coachSessionRepository.findCoachSessionByState(SessionState.ACCEPTED);
    }

    public List<CoachSession> getAllRequestedCoachSessions() {
        return coachSessionRepository.findCoachSessionByState(SessionState.REQUESTED);
    }

    public CoachSessionDto updateCoachSession(int coachSessionId, int userId, SessionState sessionState) {
//        CoachSessionStateValidation(coachSessionId, userId, sessionState);
        CoachSession coachSessionToUpdate = coachSessionRepository.findById(coachSessionId).get();
        coachSessionToUpdate.setState(sessionState);
        return coachSessionMapper.toDto(coachSessionToUpdate);
    }

    //HELPER METHODS

    private void sessionValidation(CreateCoachSessionDto createCoachSessionDto) {
        userService.assertIfUserIsACoach(createCoachSessionDto.getCoachId());
        userService.assertIfCoachCanTeachTopic(createCoachSessionDto.getCoachId(), createCoachSessionDto.getTopicId());
    }

//    private void CoachSessionStateValidation(int coachSessionId, int userId, SessionState sessionState) {
//        if(!this.userService.isAdmin(userId)){
//            this.securityService.assertIfUserIdMatchesJWTTokenId(userId);
//            this.securityService.assertIfCoachSessionExist(coachSessionId);
//            this.securityService.assertIfUserIsInTheCoachSession(coachSessionId, userId);
//            this.securityService.assertIfSessionStateIsAllowedToChange(coachSessionId, userId, sessionState);
//        }
//    }

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

}
