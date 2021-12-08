package com.example.coachwithme.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
//@Builder
@Table(name = "user_feedback")
public class UserFeedback {

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "user_feedback_seq", sequenceName = "USER_FEEDBACK_ID_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_feedback_seq")
    private int id;

    @Column(name = "coach_exploration_rating")
    private int coachExplanationRating;

    @Column(name = "coach_session_usefull_rating")
    private int coachSessionUsefullRating;

    @Column(name = "message")
    private String message;

}
