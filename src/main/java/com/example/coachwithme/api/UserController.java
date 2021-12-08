package com.example.coachwithme.api;

import com.example.coachwithme.dto.CreateUserDto;
import com.example.coachwithme.dto.UserDto;
import com.example.coachwithme.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/users")
public class UserController {
    private final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@Valid @RequestBody CreateUserDto createUserDto) {
        logger.info("User is created with name: " + createUserDto.getName());
        return userService.registerUser(createUserDto);
    }

    @GetMapping(path = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public UserDto getUserOverview(@PathVariable int userId){
        logger.info("UserOverview for User with ID :"+ userId+" is retrieved.");
        return userService.showUserProfileInfo(userId);
    }
}
