package com.example.coachwithme.model.user.coach;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Table(name = "coach_feedback")
public class CoachFeedback {
    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "coach_feedback_seq", sequenceName = "COACH_FEEDBACK_ID_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "coach_feedback_seq")
    private int id;
    @Column(name = "coachee_prepared_rating")
    private int coacheePreparedRating;
    @Column(name = "coachee_progress_rating")
    private int coacheeProgressRating;
    @Column(name = "message")
    private String message;

}
