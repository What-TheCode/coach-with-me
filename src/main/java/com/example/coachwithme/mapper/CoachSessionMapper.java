package com.example.coachwithme.mapper;

import com.example.coachwithme.dto.coachsession.CoachSessionDto;
import com.example.coachwithme.dto.coachsession.CreateCoachSessionDto;
import com.example.coachwithme.model.coachSession.CoachSession;
import com.example.coachwithme.model.coachSession.SessionState;
import com.example.coachwithme.repository.TopicRepository;
import com.example.coachwithme.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CoachSessionMapper {
    private final UserRepository userRepository;
    private final TopicRepository topicRepository;

    public CoachSession toEntity(CreateCoachSessionDto createCoachSessionDto){
        return CoachSession.builder()
                .subject(topicRepository.getById(createCoachSessionDto.getTopicId()))
                .date(createCoachSessionDto.getDate())
                .time(createCoachSessionDto.getTime())
                .location(createCoachSessionDto.getLocation())
                .coach(userRepository.getById(createCoachSessionDto.getCoachId()))
                .coachee(userRepository.getById(createCoachSessionDto.getCoacheeId()))
                .state(SessionState.REQUESTED)
                .build();
    }

    public CoachSessionDto toDto(CoachSession coachSession){
        return CoachSessionDto.builder()
                .id(coachSession.getId())
                .subject(coachSession.getSubject())
                .date(coachSession.getDate())
                .time(coachSession.getTime())
                .location(coachSession.getLocation())
                .remarks(coachSession.getRemarks())
                .coach(coachSession.getCoach())
                .coachee(coachSession.getCoachee())
                .state(coachSession.getState())
                .feedback(coachSession.getFeedback())
                .build();
    }
}
