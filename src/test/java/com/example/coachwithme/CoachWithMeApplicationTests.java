package com.example.coachwithme;

import com.example.coachwithme.api.CoachController;
import com.example.coachwithme.api.CoachSessionController;
import com.example.coachwithme.api.UserController;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CoachWithMeApplicationTests {

    @Test
    void contextLoads() {
    }

    private UserController userController;
    private CoachController coachController;
    private CoachSessionController coachSessionController;

    @Autowired
    public CoachWithMeApplicationTests(UserController userController, CoachController coachController, CoachSessionController coachSessionController) {
        this.userController = userController;
        this.coachController = coachController;
        this.coachSessionController = coachSessionController;
    }

    @Test
    void areAllRelevantBeansAvailable() {
        Assertions.assertThat(userController).isNotNull();
        Assertions.assertThat(coachController).isNotNull();
        Assertions.assertThat(coachSessionController).isNotNull();
    }

}
