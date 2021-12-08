package com.example.coachwithme.model.coachSession;

import com.example.coachwithme.model.coachSession.topic.Topic;
import com.example.coachwithme.model.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;


@Entity
//@Builder
@Getter
@NoArgsConstructor
@Table(name = "coach_session")
public class CoachSession {

    @Id
    @SequenceGenerator(name = "coach_session_seq", sequenceName = "COACH_SESSION_ID_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "coach_session_seq")
    private int id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "topic_id", referencedColumnName = "id")
    private Topic subject;

    @Column(name = "date", columnDefinition = "TIMESTAMP")
    private LocalDate date;

    @Column(name = "time", columnDefinition = "TIMESTAMP")
    private LocalTime time;

    @Column(name = "location")
    private String location;

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

}
