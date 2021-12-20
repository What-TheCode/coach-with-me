package com.example.coachwithme.dto.coachsession.feedback;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CoacheeFeedbackDto {

    private int id;

    @Min(value = 1, message = "Value cannot be lower than 1.")
    @Max(value = 5, message = "Value cannot be higher than 5.")
    private int coachExplanationRating;

    @Min(value = 1, message = "Value cannot be lower than 1.")
    @Max(value = 5, message = "Value cannot be higher than 5.")
    private int coachSessionUsefullRating;

    @NotBlank
    private String message;

}
