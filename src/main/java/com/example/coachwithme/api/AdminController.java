package com.example.coachwithme.api;

import com.example.coachwithme.dto.coachsession.CoachSessionDto;
import com.example.coachwithme.dto.coachsession.topic.CreateTopicDto;
import com.example.coachwithme.dto.coachsession.topic.TopicDto;
import com.example.coachwithme.dto.user.UserDto;
import com.example.coachwithme.service.AdminService;
import com.example.coachwithme.service.CoachSessionService;
import com.example.coachwithme.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/admin")
public class AdminController {

    private final AdminService adminService;
    private final UserService userService;
    private final CoachSessionService coachSessionService;

    @PostMapping(path = "/topic", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public TopicDto createTopic(@RequestBody CreateTopicDto createTopicDto) {
        return adminService.addTopic(createTopicDto);
    }

    @GetMapping(path = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(path = "/sessions", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<CoachSessionDto> getAlCoachingSessions() {
        return coachSessionService.getAllCoachingSessions();
    }
}
