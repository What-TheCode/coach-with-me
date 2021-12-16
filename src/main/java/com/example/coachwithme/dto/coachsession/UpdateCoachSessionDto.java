package com.example.coachwithme.dto.coachsession;

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

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UpdateCoachSessionDto {

    private int userId;

    private SessionState state;

}
