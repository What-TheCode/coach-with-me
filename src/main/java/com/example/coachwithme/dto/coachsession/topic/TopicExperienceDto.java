package com.example.coachwithme.dto.coachsession.topic;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TopicExperienceDto {

    private int id;
    private int experience;
    private TopicDto topic;
}
