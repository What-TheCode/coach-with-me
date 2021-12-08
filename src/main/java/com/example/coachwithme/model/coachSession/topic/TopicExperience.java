package com.example.coachwithme.model.coachSession.topic;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
//@Builder
@Table(name = "topic_experience")
public class TopicExperience {

    @Id
    @SequenceGenerator(name = "topic_experience_seq", sequenceName = "TOPIC_EXPERIENCE_ID_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "topic_experience_seq")
    private int id;

    @Column(name = "experience")
    private int experience;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "topic_id")
    private Topic topic;

}
