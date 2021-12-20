package com.example.coachwithme.dto.coachsession.topic;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UpdateTopicExperienceDto {

    private int experience;
    private int topicId;

}
