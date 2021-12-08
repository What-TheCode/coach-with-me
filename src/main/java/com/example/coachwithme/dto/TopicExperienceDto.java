package com.example.coachwithme.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Builder
@NoArgsConstructor
@Getter
public class TopicExperienceDto {

    private int id;
    private int experience;
    private TopicDto topic;
}
