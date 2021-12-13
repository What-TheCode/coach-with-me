package com.example.coachwithme.service;

import com.example.coachwithme.dto.coachsession.CoachSessionDto;
import com.example.coachwithme.dto.coachsession.CreateCoachSessionDto;
import com.example.coachwithme.mapper.coachssession.CoachSessionMapper;
import com.example.coachwithme.model.coachSession.CoachSession;
import com.example.coachwithme.repository.CoachSessionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    //TODO does coacheeId and coachId need to be include in the arguments? -> how are they added in json?
    //TODO Does the assertIfUserIsACoach method has to be in the userService? -> not private method-> so?
    public CoachSessionDto registerACoachSession(CreateCoachSessionDto createCoachSessionDto){
        sessionValidation(createCoachSessionDto);
        this.securityService.assertIfUserIdMatchesJWTTokenId(createCoachSessionDto.getCoacheeId());

        CoachSession newCoachSession = coachSessionMapper.toEntity(createCoachSessionDto);
        this.coachSessionRepository.save(newCoachSession);
        return coachSessionMapper.toDto(newCoachSession);
    }

    public List<CoachSessionDto> getCoachSessionsAsCoachee(int coacheeId) {
        this.securityService.assertIfUserIdMatchesJWTTokenId(coacheeId);

        return  this.coachSessionMapper.toDto(
                this.coachSessionRepository.findCoachSessionsByCoacheeId(coacheeId));
    }

    public List<CoachSessionDto> getCoachSessionsAsCoach(int coachId) {
        this.securityService.assertIfUserIdMatchesJWTTokenId(coachId);

        return  this.coachSessionMapper.toDto(
                this.coachSessionRepository.findCoachSessionsByCoachId(coachId));
    }


    //HELPER METHODS

    private void sessionValidation(CreateCoachSessionDto createCoachSessionDto) {
        userService.assertIfUserIsACoach(createCoachSessionDto.getCoachId());
        userService.assertIfCoachCanTeachTopic(createCoachSessionDto.getCoachId(),createCoachSessionDto.getTopicId());
    }

//    public void assertIfCoachSessionIsInTheFuture(CreateCoachSessionDto createCoachSessionDto){
//        LocalDateTime
//    }
}
