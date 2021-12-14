package com.example.coachwithme.api;

import com.example.coachwithme.dto.coachsession.topic.CreateTopicDto;
import com.example.coachwithme.dto.coachsession.topic.TopicDto;
import com.example.coachwithme.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/admin")
public class AdminController {

    private final AdminService adminService;

    @PostMapping(path = "/topic", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public TopicDto createTopic(@RequestBody CreateTopicDto createTopicDto) {
        return adminService.addTopic(createTopicDto);
    }

}
