package com.example.coachwithme.dto.coachsession;

import com.example.coachwithme.dto.coachsession.feedback.SessionFeedbackDto;
import com.example.coachwithme.dto.coachsession.topic.TopicDto;
import com.example.coachwithme.dto.user.UserDto;
import com.example.coachwithme.model.Location;
import com.example.coachwithme.model.coachSession.SessionState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CoachSessionDto {

    private int id;

    private TopicDto subject;

    private LocalDate date;

    private LocalTime time;

    private Location location;

    private String remarks;

    private UserDto coach;

    private UserDto coachee;

    private SessionState state;

    private SessionFeedbackDto feedback;
}
