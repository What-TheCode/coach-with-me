package com.example.coachwithme.mapper.user.coach;

import com.example.coachwithme.dto.user.coach.CoachDetailsCreateDto;
import com.example.coachwithme.dto.user.coach.CoachDetailsDto;
import com.example.coachwithme.mapper.coachssession.topic.TopicExperienceMapper;
import com.example.coachwithme.model.user.coach.CoachDetails;
import org.springframework.stereotype.Component;

@Component
public class CoachDetailMapper {

    private final TopicExperienceMapper topicExperienceMapper;

    public CoachDetailMapper(TopicExperienceMapper topicExperienceMapper) {
        this.topicExperienceMapper = topicExperienceMapper;
    }

    public CoachDetailsDto toDto(CoachDetails coachDetails) {

        if (coachDetails == null) {
            return null;
        }

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

    public CoachDetails toEntity(CoachDetailsCreateDto coachDetailsCreateDto) {
        return CoachDetails.builder()
                .coachExp(0)
                .coachIntroduction(coachDetailsCreateDto.getCoachIntroduction())
                .coachAvailability(coachDetailsCreateDto.getCoachAvailability())
                .coachExperiences(topicExperienceMapper.fromCreateDtoToEntities(coachDetailsCreateDto.getCoachExperiences()))
                .build();
    }
}
