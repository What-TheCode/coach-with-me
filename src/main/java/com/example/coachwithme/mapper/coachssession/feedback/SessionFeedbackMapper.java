package com.example.coachwithme.mapper.coachssession.feedback;

import com.example.coachwithme.dto.coachsession.feedback.SessionFeedbackDto;
import com.example.coachwithme.model.coachSession.feedback.SessionFeedback;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SessionFeedbackMapper {

    private final CoachFeedbackMapper coachFeedbackMapper;
    private final CoacheeFeedbackMapper coacheeFeedbackMapper;

    public SessionFeedbackDto toDto(SessionFeedback sessionFeedback) {
        if (sessionFeedback == null) {
            return null;
        }

        return SessionFeedbackDto.builder()
                .id(sessionFeedback.getId())
                .coacheeFeedbackDto(coacheeFeedbackMapper.toDto(sessionFeedback.getCoacheeFeedback()))
                .coachFeedbackDto(coachFeedbackMapper.toDto(sessionFeedback.getCoachFeedback()))
                .build();
    }

    public SessionFeedback toEntity(SessionFeedbackDto sessionFeedbackDto) {
        return SessionFeedback.builder()
                .coacheeFeedback(coacheeFeedbackMapper.toEntity(sessionFeedbackDto.getCoacheeFeedbackDto()))
                .coachFeedback(coachFeedbackMapper.toEntity(sessionFeedbackDto.getCoachFeedbackDto()))
                .build();
    }

}
