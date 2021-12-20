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
public class CreateCoachFeedbackDto {

    private int coacheePreparedRating;

    private int coacheeProgressRating;

    private String message;

}
