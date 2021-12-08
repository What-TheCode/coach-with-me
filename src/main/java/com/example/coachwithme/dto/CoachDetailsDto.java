package com.example.coachwithme.dto;

import com.example.coachwithme.model.coachSession.topic.TopicExperience;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@Getter
public class CoachDetailsDto {

    private int id;
    private int coachExp;
    private String coachIntroduction;
    private String coachAvailability;
    private List<TopicExperienceDto> coachExperiences;

}
