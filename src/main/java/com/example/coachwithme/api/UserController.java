package com.example.coachwithme.api;

import com.example.coachwithme.dto.coachsession.topic.TopicDto;
import com.example.coachwithme.dto.user.CoachDto;
import com.example.coachwithme.dto.user.CreateUserDto;
import com.example.coachwithme.dto.user.UpdateUserDto;
import com.example.coachwithme.dto.user.UserDto;
import com.example.coachwithme.service.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.security.PermitAll;
import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@Slf4j
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = "/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@Valid @RequestBody CreateUserDto createUserDto) {
        log.info("User is created with name: " + createUserDto.getName());
        return userService.registerUser(createUserDto);
    }

    @GetMapping(path = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public UserDto getUserOverview(@PathVariable int userId) {
        log.info("UserOverview for User with ID :" + userId + " is requested.");
        return userService.showUserProfileInfo(userId);
    }

    @PostMapping(path = "/{userId}/edit", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public UserDto editUserOverview(@PathVariable int userId,
                                    @Valid @RequestBody UpdateUserDto updateUserDto) {
        log.info("Edit UserOverview for User with ID :" + userId + " is requested.");
        return userService.editUserProfileInfo(userId, updateUserDto);
    }

    @GetMapping(path = "/check/{userEmail}", produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String checkIfEmailAlreadyExists(@PathVariable String userEmail) {
        log.info("Checking if email " + userEmail + " already exists");
        return userService.checkIfEmailExistsInDatabase(userEmail);
    }


    // show the page of all coaches
    //Filter by name and email will be implemented in the frontend
    // Filter by topic will be here is optional
    @GetMapping(path = "/findACoach", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<CoachDto> findCoach(@RequestParam(required = false)  String topicSelectedName) {
        if(topicSelectedName != null) {
            return userService.getTheCoachesByTopic(topicSelectedName);
        }
        return userService.getTheCoaches();
    }



    @GetMapping(path = "/coaches/{coachId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public UserDto getCoachOverview(@PathVariable int coachId) {
        log.info("Coach Overview for coach with ID :" + coachId + " is requested.");
        return userService.showCoachProfileInfo(coachId);
    }


    @GetMapping(path = "/topics", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<TopicDto> getAllTopics() {
        return userService.getTopicsNames();
    }


}
