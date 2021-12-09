package com.example.coachwithme.api;


import com.example.coachwithme.dto.CoachDetailsDto;
import com.example.coachwithme.dto.UserDto;
import com.example.coachwithme.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/coaches")
@RequiredArgsConstructor
public class CoachController {

    private UserService userService;

    @PutMapping(consumes = org.springframework.http.MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto upGradeToCoach(CoachDetailsDto coachDetailsDto){
        return userService.upGradeToCoach(coachDetailsDto);
    }

}
