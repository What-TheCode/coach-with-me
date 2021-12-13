package com.example.coachwithme.dto.user.coach;

import com.example.coachwithme.dto.coachsession.topic.CreateTopicExperienceDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CoachDetailsCreateDto {

    @NotBlank(message = "Introduction cannot be empty.")
    private String coachIntroduction;

    @NotBlank(message = "Availability cannot be empty.")
    private String coachAvailability;

    @Valid
    private List<CreateTopicExperienceDto> coachExperiences;

}