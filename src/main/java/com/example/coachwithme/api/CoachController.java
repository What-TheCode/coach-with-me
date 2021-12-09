package com.example.coachwithme.api;


import com.example.coachwithme.dto.CoachDetailsCreateDto;
import com.example.coachwithme.dto.UserDto;
import com.example.coachwithme.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/coaches")
@RequiredArgsConstructor
public class CoachController {

    private UserService userService;

    public CoachController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping(path = "/{userId}", consumes = org.springframework.http.MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto upGradeToCoach(@PathVariable int userId,
                                  @RequestBody CoachDetailsCreateDto coachDetailsCreateDto) {
        return userService.upGradeToCoach(coachDetailsCreateDto, userId);
    }
}
