package com.example.coachwithme.dto.coachsession.feedback;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CoacheeFeedbackDto {

    private int id;

    private int coachExplanationRating;

    private int coachSessionUsefullRating;

    private String message;

}
