package com.example.coachwithme.dto.coachsession.feedback;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreateCoacheeFeedbackDto {

    private int coachExplanationRating;

    private int coachSessionUsefullRating;

    private String message;

}
