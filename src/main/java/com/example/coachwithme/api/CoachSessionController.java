package com.example.coachwithme.api;

import com.example.coachwithme.dto.coachsession.CoachSessionDto;
import com.example.coachwithme.dto.coachsession.CreateCoachSessionDto;
import com.example.coachwithme.service.CoachSessionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/sessions")
public class CoachSessionController {
    private final CoachSessionService coachSessionService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
//    @PreAuthorize("hasAnyRole('COACHEE')")
    public CoachSessionDto createSession(@Valid @RequestBody CreateCoachSessionDto createCoachSessionDto) {
        log.info("Session with id");
        return coachSessionService.registerACoachSession(createCoachSessionDto);
    }
}
