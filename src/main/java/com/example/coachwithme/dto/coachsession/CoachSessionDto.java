package com.example.coachwithme.dto.coachsession;

import com.example.coachwithme.model.coachSession.SessionFeedback;
import com.example.coachwithme.model.coachSession.SessionState;
import com.example.coachwithme.model.coachSession.topic.Topic;
import com.example.coachwithme.model.user.User;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
@Getter
@Builder
public class CoachSessionDto {

    private int id;

    private TopicDto subject;

    private LocalDate date;

    private LocalTime time;

    private String location;

    private String remarks;

    private UserDto coach;

    private UserDto coachee;

    private SessionState state;

    private SessionFeedbackDto feedback;
}
