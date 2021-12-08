package com.example.coachwithme.model.coachSession.topic;

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
@Table(name = "topic")
public class Topic {

    @Id
    @SequenceGenerator(name = "topic_seq", sequenceName = "TOPIC_ID_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "topic_seq")
    private int id;

    @Column(name = "name")
    private String name;
}
