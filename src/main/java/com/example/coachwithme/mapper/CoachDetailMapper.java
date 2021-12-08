package com.example.coachwithme.mapper;

import com.example.coachwithme.dto.CoachDetailsDto;
import com.example.coachwithme.model.user.coach.CoachDetails;
import org.springframework.stereotype.Component;

@Component
public class CoachDetailMapper {

    private final TopicExperienceMapper topicExperienceMapper;

    public CoachDetailMapper(TopicExperienceMapper topicExperienceMapper) {
        this.topicExperienceMapper = topicExperienceMapper;
    }

    public CoachDetailsDto toDto(CoachDetails coachDetails){
        return CoachDetailsDto.builder()
                .id(coachDetails.getId())
                .coachExp(coachDetails.getCoachExp())
                .coachIntroduction(coachDetails.getCoachIntroduction())
                .coachAvailability(coachDetails.getCoachAvailability())
                .coachExperiences(topicExperienceMapper.toDtos(coachDetails.getCoachExperiences()))
                .build();
    }

    public CoachDetails toEntity(CoachDetailsDto coachDetailsDto) {
        return CoachDetails.builder()
                .id(coachDetailsDto.getId())
                .coachExp(coachDetailsDto.getCoachExp())
                .coachIntroduction(coachDetailsDto.getCoachIntroduction())
                .coachAvailability(coachDetailsDto.getCoachAvailability())
                .coachExperiences(topicExperienceMapper.toEntities(coachDetailsDto.getCoachExperiences()))
                .build();
    }
}
