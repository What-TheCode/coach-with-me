package com.example.coachwithme.api;


import com.example.coachwithme.dto.user.UserDto;
import com.example.coachwithme.dto.user.coach.UpdateCoachDto;
import com.example.coachwithme.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/coaches")
public class CoachController {

    private final UserService userService;

    @PostMapping(path = "/becomecoach/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto upGradeToCoach(@PathVariable int userId) {
        return userService.upGradeToCoach(userId);
    }

    @PutMapping(path = "/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public UserDto updateCoach(@PathVariable int userId, @Valid @RequestBody UpdateCoachDto updateCoachDto) {
        return userService.updateCoach(userId, updateCoachDto);
    }

}
