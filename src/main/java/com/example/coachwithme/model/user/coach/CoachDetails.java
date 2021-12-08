package com.example.coachwithme.model.user.coach;

import com.example.coachwithme.model.coachSession.topic.TopicExperience;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


@Getter
@NoArgsConstructor
@Builder
@Entity
@Table(name = "coach_details")
public class CoachDetails {

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "coach_details_seq", sequenceName = "COACH_DETAILS_ID_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "coach_details_seq")
    private int id;

    @Column(name = "coach_exp")
    private int coachExp;

    @Column(name = "coach_introduction")
    private String coachIntroduction;

    @Column(name = "coach_availability")
    private String coachAvailability;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private List<TopicExperience> coachExperiences;

}