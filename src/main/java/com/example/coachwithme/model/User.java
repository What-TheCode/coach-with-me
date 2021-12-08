package com.example.coachwithme.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
//@Builder
@Table(name = "user_table")
public class User {

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "user_seq", sequenceName = "USER_ID_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    private int id;

    @Embedded
    private Name name;

    @Column(name = "email")
    private String email;

    @Column(name = "company")
    private String company;

    @ElementCollection(targetClass = UserRole.class)
    @JoinColumn(name = "user_id")
    @Enumerated(EnumType.STRING)
    private List<UserRole> userRoles;

    @Column(name = "picture_url")
    private String pictureUrl;

    @Column(name = "password")
    private String password;

    @Embedded
    private CoachDetails coachDetails;

}
