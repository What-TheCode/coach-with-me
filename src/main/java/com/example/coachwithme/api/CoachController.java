package com.example.coachwithme.api;


import com.example.coachwithme.dto.CoachDetailsCreateDto;
import com.example.coachwithme.dto.UpdateCoachDto;
import com.example.coachwithme.dto.UserDto;
import com.example.coachwithme.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/coaches")
public class CoachController {

    private final UserService userService;

    public CoachController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping(path = "/upgrade/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto upGradeToCoach(@PathVariable int userId,
                                  @Valid @RequestBody CoachDetailsCreateDto coachDetailsCreateDto) {
        return userService.upGradeToCoach(coachDetailsCreateDto, userId);
    }

    @PutMapping(path = "/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public UserDto updateCoach(@PathVariable int userId, @Valid @RequestBody UpdateCoachDto updateCoachDto){
        return userService.updateCoach(userId, updateCoachDto);
    }


}
