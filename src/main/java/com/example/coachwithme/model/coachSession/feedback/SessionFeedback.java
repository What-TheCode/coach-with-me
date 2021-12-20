package com.example.coachwithme.model.coachSession.feedback;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Entity
@Table(name = "session_feedback")
public class SessionFeedback {
    @Id
    @SequenceGenerator(name = "session_feedback_seq", sequenceName = "SESSION_FEEDBACK_ID_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "session_feedback_seq")
    private int id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_feedback_id", referencedColumnName = "id")
    private CoacheeFeedback coacheeFeedback;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "coach_feedback_id", referencedColumnName = "id")
    private CoachFeedback coachFeedback;

    public void setCoacheeFeedback(CoacheeFeedback coacheeFeedback) {
        this.coacheeFeedback = coacheeFeedback;
    }

    public void setCoachFeedback(CoachFeedback coachFeedback) {
        this.coachFeedback = coachFeedback;
    }
}
