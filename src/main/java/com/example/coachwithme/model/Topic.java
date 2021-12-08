package com.example.coachwithme.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@Table(name = "topic")
public class Topic {

    @Id
    @SequenceGenerator(name = "topic_seq", sequenceName = "TOPIC_ID_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "topic_seq")
    private int id;

    @Column(name = "name")
    private String name;
}
