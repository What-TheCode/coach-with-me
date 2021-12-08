package com.example.coachwithme;

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

    @Autowired
    public CoachWithMeApplicationTests(UserController userController){
        this.userController=userController;
    }

    @Test
    void areAllRelevantBeansAvailable(){
        Assertions.assertThat(userController).isNotNull();
    }

}
