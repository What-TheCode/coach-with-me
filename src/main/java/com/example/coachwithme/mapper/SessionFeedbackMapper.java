package com.example.coachwithme.mapper;

import com.example.coachwithme.dto.coachsession.SessionFeedbackDto;
import com.example.coachwithme.model.coachSession.SessionFeedback;
import org.springframework.stereotype.Component;

@Component
public class SessionFeedbackMapper {

    public SessionFeedbackDto toDto(SessionFeedback sessionFeedback) {
        if(sessionFeedback == null) return null;

        return SessionFeedbackDto.builder()
                .id(sessionFeedback.getId())
                .userFeedback(sessionFeedback.getUserFeedback())
                .coachFeedback(sessionFeedback.getCoachFeedback())
                .build();
    }

}
