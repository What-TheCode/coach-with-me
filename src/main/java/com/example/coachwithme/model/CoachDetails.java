package com.example.coachwithme.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Embeddable
@Getter
@NoArgsConstructor
@Builder
@Table(name = "coach_details")
public class CoachDetails {

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
