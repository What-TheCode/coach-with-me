package com.example.coachwithme.mapper.coachssession.feedback;

import com.example.coachwithme.dto.coachsession.feedback.CoacheeFeedbackDto;
import com.example.coachwithme.dto.coachsession.feedback.CreateCoacheeFeedbackDto;
import com.example.coachwithme.model.coachSession.feedback.CoacheeFeedback;
import org.springframework.stereotype.Component;

@Component
public class CoacheeFeedbackMapper {

    public CoacheeFeedback toEntity(CoacheeFeedbackDto coacheeFeedbackDto) {
        return CoacheeFeedback.builder()
                .id(coacheeFeedbackDto.getId())
                .coachExplanationRating(coacheeFeedbackDto.getCoachExplanationRating())
                .coachSessionUsefullRating(coacheeFeedbackDto.getCoachSessionUsefullRating())
                .message(coacheeFeedbackDto.getMessage())
                .build();
    }

    public CoacheeFeedback toEntity(CreateCoacheeFeedbackDto createCoacheeFeedbackDto) {
        return CoacheeFeedback.builder()
                .coachExplanationRating(createCoacheeFeedbackDto.getCoachExplanationRating())
                .coachSessionUsefullRating(createCoacheeFeedbackDto.getCoachSessionUsefullRating())
                .message(createCoacheeFeedbackDto.getMessage())
                .build();
    }

    public CoacheeFeedbackDto toDto(CoacheeFeedback coacheeFeedback) {

        if (coacheeFeedback == null) {
            return null;
        }

        return CoacheeFeedbackDto.builder()
                .id(coacheeFeedback.getId())
                .coachExplanationRating(coacheeFeedback.getCoachExplanationRating())
                .coachSessionUsefullRating(coacheeFeedback.getCoachSessionUsefullRating())
                .message(coacheeFeedback.getMessage())
                .build();
    }

}
