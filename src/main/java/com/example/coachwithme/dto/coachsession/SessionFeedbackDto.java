package com.example.coachwithme.dto.coachsession;

import com.example.coachwithme.model.user.UserFeedback;
import com.example.coachwithme.model.user.coach.CoachFeedback;
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

    private UserFeedback userFeedback;

    private CoachFeedback coachFeedback;
}
