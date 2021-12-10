package com.example.coachwithme.dto.coachsession;

import com.example.coachwithme.model.coachSession.SessionState;
import com.example.coachwithme.model.coachSession.topic.Topic;
import com.example.coachwithme.model.user.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
public class CreateCoachSessionDto {

    @NotBlank(message = "Topic cannot be empty")
    private int topicId;
    @Future(message = "Book a time in the future")
    private LocalDate date;
    @NotBlank(message = "Time cannot be empty")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "HH:mm:ss[.SSS][.SS][.S]")
    private LocalTime time;
    @NotBlank(message = "Location cannot be empty")
    private String location;
    private String remarks;
    private int coachId;
    private int coacheeId;

}
