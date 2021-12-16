package com.example.coachwithme.api;

import com.example.coachwithme.dto.coachsession.CoachSessionDto;
import com.example.coachwithme.dto.coachsession.CreateCoachSessionDto;
import com.example.coachwithme.dto.coachsession.UpdateCoachSessionDto;
import com.example.coachwithme.model.coachSession.SessionState;
import com.example.coachwithme.service.CoachSessionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
        log.info("Session with coachee with id "+ createCoachSessionDto.getCoacheeId()+" and coach with id "+createCoachSessionDto.getCoachId()+".");
        return coachSessionService.registerACoachSession(createCoachSessionDto);
    }

    @GetMapping(path = "/coachee/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    //    @PreAuthorize("hasAnyRole('COACHEE')")
    public List<CoachSessionDto> getCoachSessionsOverviewForCoachee(@PathVariable int userId) {
        return this.coachSessionService.getCoachSessionsAsCoachee(userId);
    }

    @GetMapping(path = "/coach/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    //    @PreAuthorize("hasAnyRole('COACHEE')")
    public List<CoachSessionDto> getCoachSessionsOverviewForCoach(@PathVariable int userId) {
        return this.coachSessionService.getCoachSessionsAsCoach(userId);
    }


    @PatchMapping(path = "/{coachSessionId}" ,consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasAnyRole('COACHEE','COACH','ADMIN')")
    public CoachSessionDto editStatusCoachSession(@PathVariable int coachSessionId
            ,@Valid @RequestBody UpdateCoachSessionDto updateCoachSessionDto) {
        log.info("Coachsession with id " + coachSessionId + " is changed by user with id" + updateCoachSessionDto.getUserId() + " to status " + updateCoachSessionDto.getState() + ".");
        return this.coachSessionService.updateCoachSession(coachSessionId,updateCoachSessionDto);
    }
}
