package com.example.coachwithme.dto.coachsession.feedback;

import com.example.coachwithme.model.coachSession.feedback.CoacheeFeedback;
import com.example.coachwithme.model.coachSession.feedback.CoachFeedback;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SessionFeedbackDto {

    private int id;

    private CoacheeFeedbackDto coacheeFeedbackDto;

    private CoachFeedbackDto coachFeedbackDto;
}
