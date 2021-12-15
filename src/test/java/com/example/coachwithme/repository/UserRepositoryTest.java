package com.example.coachwithme.repository;

import com.example.coachwithme.model.user.Name;
import com.example.coachwithme.model.user.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

//
//    @AfterEach
//    void tearDown(){
//        userRepository.deleteAll();
//    }


    @Test
    void WhenExistingUserIsRecalledByFindByEmailMethod_ThenThisUserIsFetched() {
        //GIVEN
        String email = "email@test.com";

        User user = User.builder()
                .name(new Name("firstName", "lastName"))
                .company("DC corp")
                .email(email)
                .pictureUrl("none")
                .coachDetails(null)
                .build();
        userRepository.save(user);
        //WHEN
        User userOutOfRepo = userRepository.findByEmail(email);
        //THEN
        assertThat(userOutOfRepo.getEmail()).isEqualTo(email);
    }

    @Test
    void WhenFindByEmailMethodIsUsedForAWrongEmail_ThenNothingIsFetched() {
        //GIVEN
        String wrongEmail = "wrongemail@test.com";

        //WHEN
        User userOutOfRepo = userRepository.findByEmail(wrongEmail);
        //THEN
        assertThat(userOutOfRepo).isNull();
    }

}