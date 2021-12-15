package com.example.coachwithme.repository;

import com.example.coachwithme.model.Location;
import com.example.coachwithme.model.coachSession.CoachSession;
import com.example.coachwithme.model.coachSession.topic.Topic;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CoachSessionRepositoryTest {

    @Autowired
    CoachSessionRepository coachSessionRepository;
    CoachSession coachSession1;
    CoachSession coachSession2;
    CoachSession coachSession3;
    CoachSession coachSession4;

    @BeforeEach
    void setUp() {

    }
}