package com.example.coachwithme.api;

import com.example.coachwithme.dto.CreateUserDto;
import com.example.coachwithme.dto.UpdateUserDto;
import com.example.coachwithme.dto.UserDto;
import com.example.coachwithme.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Slf4j
@CrossOrigin
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

    @PutMapping(path = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public UserDto editUserOverview(@PathVariable int userId,
                                    @Valid @RequestBody UpdateUserDto updateUserDto) {
        log.info("Edit UserOverview for User with ID :" + userId + " is requested.");
        return userService.editUserProfileInfo(userId, updateUserDto);
    }

}
