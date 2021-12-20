package com.example.coachwithme.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class ScheduledService {

    private static final int UPDATE_TIME_IN_MINUTES = 5;

    private final CoachSessionService coachSessionService;

    @Scheduled(fixedRate = UPDATE_TIME_IN_MINUTES * 60 * 1000)
    public void checkForOverdueCoachSessions() {
        coachSessionService.checkForOverdueAcceptedCoachSessions();
        coachSessionService.checkForOverdueRequestedCoachSessions();
    }

}
