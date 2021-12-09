package com.example.coachwithme.model.user.coach;

import com.example.coachwithme.model.coachSession.topic.TopicExperience;
import lombok.*;

import javax.persistence.*;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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

    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private List<TopicExperience> coachExperiences;

}
