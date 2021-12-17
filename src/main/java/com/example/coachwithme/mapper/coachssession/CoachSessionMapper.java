package com.example.coachwithme.mapper.coachssession;

import com.example.coachwithme.dto.coachsession.CoachSessionDto;
import com.example.coachwithme.dto.coachsession.CreateCoachSessionDto;
import com.example.coachwithme.dto.coachsession.LocationDto;
import com.example.coachwithme.mapper.SessionFeedbackMapper;
import com.example.coachwithme.mapper.coachssession.topic.TopicMapper;
import com.example.coachwithme.mapper.user.UserMapper;
import com.example.coachwithme.model.Location;
import com.example.coachwithme.model.coachSession.CoachSession;
import com.example.coachwithme.model.coachSession.SessionState;
import com.example.coachwithme.repository.TopicRepository;
import com.example.coachwithme.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CoachSessionMapper {
    private final UserRepository userRepository;
    private final TopicRepository topicRepository;
    private final TopicMapper topicMapper;
    private final UserMapper userMapper;
    private final SessionFeedbackMapper feedbackMapper;

    public CoachSession toEntity(CreateCoachSessionDto createCoachSessionDto){

        return CoachSession.builder()
                .subject(topicRepository.getById(createCoachSessionDto.getTopicId()))
                .date(createCoachSessionDto.getDate())
                .time(LocalTime.parse(createCoachSessionDto.getTime(),
                        DateTimeFormatter.ofPattern("HH:mm")))
                .location(getTheLocation(createCoachSessionDto.getLocation()))
                .coach(userRepository.getById(createCoachSessionDto.getCoachId()))
                .coachee(userRepository.getById(createCoachSessionDto.getCoacheeId()))
                .state(SessionState.REQUESTED)
                .build();
    }


    public List<CoachSessionDto> toDto(List<CoachSession> coachSessions) {
        return coachSessions.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public CoachSessionDto toDto(CoachSession coachSession){
        return CoachSessionDto.builder()
                .id(coachSession.getId())
                .subject(this.topicMapper.toDto(coachSession.getSubject()))
                .date(coachSession.getDate())
                .time(coachSession.getTime())
                .location(coachSession.getLocation())
                .remarks(coachSession.getRemarks())
                .coach(this.userMapper.toDto(coachSession.getCoach()))
                .coachee(this.userMapper.toDto(coachSession.getCoach()))
                .state(coachSession.getState())
                .feedback(this.feedbackMapper.toDto(coachSession.getFeedback()))
                .build();
    }


    // In order to set the Enum value according the request from the user in the frontend
    private Location getTheLocation(LocationDto locationDto){
        if(locationDto.getLocationName().equalsIgnoreCase("ONLINE")){
            return Location.ONLINE;
        }
            return Location.F2F;

    }

}
