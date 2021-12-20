package com.example.coachwithme.mapper.coachssession.feedback;

import com.example.coachwithme.dto.coachsession.feedback.CoachFeedbackDto;
import com.example.coachwithme.dto.coachsession.feedback.CreateCoachFeedbackDto;
import com.example.coachwithme.model.coachSession.feedback.CoachFeedback;
import org.springframework.stereotype.Component;

@Component
public class CoachFeedbackMapper {


    public CoachFeedback toEntity(CoachFeedbackDto coachFeedbackDto) {
        return CoachFeedback.builder()
                .id(coachFeedbackDto.getId())
                .coacheePreparedRating(coachFeedbackDto.getCoacheePreparedRating())
                .coacheeProgressRating(coachFeedbackDto.getCoacheeProgressRating())
                .message(coachFeedbackDto.getMessage())
                .build();
    }

    public CoachFeedback toEntity(CreateCoachFeedbackDto createCoachFeedbackDto) {
        return CoachFeedback.builder()
                .coacheePreparedRating(createCoachFeedbackDto.getCoacheePreparedRating())
                .coacheeProgressRating(createCoachFeedbackDto.getCoacheeProgressRating())
                .message(createCoachFeedbackDto.getMessage())
                .build();
    }

    public CoachFeedbackDto toDto(CoachFeedback coachFeedback) {

        if (coachFeedback == null) {
            return null;
        }

        return CoachFeedbackDto.builder()
                .id(coachFeedback.getId())
                .coacheePreparedRating(coachFeedback.getCoacheePreparedRating())
                .coacheeProgressRating(coachFeedback.getCoacheeProgressRating())
                .message(coachFeedback.getMessage())
                .build();
    }

}
