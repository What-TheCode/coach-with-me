package com.example.coachwithme.dto.coachsession.feedback;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CoachFeedbackDto {

    private int id;

    private int coacheePreparedRating;

    private int coacheeProgressRating;

    private String message;

}
