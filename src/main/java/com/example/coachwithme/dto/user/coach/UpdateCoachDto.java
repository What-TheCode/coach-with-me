package com.example.coachwithme.dto.user.coach;


import com.example.coachwithme.dto.coachsession.topic.CreateTopicExperienceDto;
import com.example.coachwithme.dto.coachsession.topic.TopicExperienceDto;
import com.example.coachwithme.dto.coachsession.topic.UpdateTopicExperienceDto;
import com.example.coachwithme.model.coachSession.topic.TopicExperience;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.swing.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCoachDto {


    @NotBlank(message = "Introduction cannot be empty.")
    private String coachIntroduction;

    @NotBlank(message = "Availability cannot be empty.")
    private String coachAvailability;

}
