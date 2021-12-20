package com.example.coachwithme.model.coachSession;

import com.example.coachwithme.model.Location;
import com.example.coachwithme.model.coachSession.topic.Topic;
import com.example.coachwithme.model.user.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Table(name = "coach_session")
public class CoachSession {

    @Id
    @SequenceGenerator(name = "coach_session_seq", sequenceName = "COACH_SESSION_ID_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "coach_session_seq")
    private int id;

    @ManyToOne
    @JoinColumn(name = "topic_id", referencedColumnName = "id")
    private Topic subject;

    @Column(name = "date", columnDefinition = "DATE")
    private LocalDate date;

    @Column(name = "time", columnDefinition = "TIME")
    private LocalTime time;

    @Column(name = "location")
    @Enumerated(EnumType.STRING)
    private Location location;

    @Column(name = "remarks")
    private String remarks;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "coach_id", referencedColumnName = "id")
    private User coach;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "coachee_id", referencedColumnName = "id")
    private User coachee;

    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private SessionState state;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "session_feedback_id", referencedColumnName = "id")
    private SessionFeedback feedback;

    public void setState(SessionState state) {
        this.state = state;
    }

}
