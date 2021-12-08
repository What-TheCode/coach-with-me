package com.example.coachwithme.model.coachSession;

import com.example.coachwithme.model.user.UserFeedback;
import com.example.coachwithme.model.user.coach.CoachFeedback;
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
    private UserFeedback userFeedback;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "coach_feedback_id", referencedColumnName = "id")
    private CoachFeedback coachFeedback;

}
