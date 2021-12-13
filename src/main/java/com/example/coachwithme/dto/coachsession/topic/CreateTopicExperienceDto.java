package com.example.coachwithme.dto.coachsession.topic;

import lombok.Getter;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
public class CreateTopicExperienceDto {

    @Min(value = 1, message = "Value cannot be lower than 1.")
    @Max(value = 5, message = "Value cannot be higher than 5.")
    private int experience;

    @Valid
    private CreateTopicDto topic;

}
