package com.example.coachwithme.service;

import com.example.coachwithme.dto.coachsession.CoachSessionDto;
import com.example.coachwithme.dto.coachsession.CreateCoachSessionDto;
import com.example.coachwithme.mapper.CoachSessionMapper;
import com.example.coachwithme.model.coachSession.CoachSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class CoachSessionService {
    private final CoachSessionMapper coachSessionMapper;
    private final UserService userService;

    //TODO does coacheeId and coachId need to be include in the arguments? -> how are they added in json?
    //TODO Does the assertIfUserIsACoach method has to be in the userService? -> not private method-> so?
    public CoachSessionDto registerACoachSession(CreateCoachSessionDto createCoachSessionDto){
        userService.assertIfUserIsACoach(createCoachSessionDto.getCoachId());
        CoachSession coachSession = coachSessionMapper.toEntity(createCoachSessionDto);
        return coachSessionMapper.toDto(coachSession);
    }

}
