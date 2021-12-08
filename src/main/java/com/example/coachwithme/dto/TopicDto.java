package com.example.coachwithme.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@Getter
public class TopicDto {

    private int id;
    private String name;

}
