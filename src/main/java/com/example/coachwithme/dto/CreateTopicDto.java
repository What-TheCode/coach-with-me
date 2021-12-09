package com.example.coachwithme.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class CreateTopicDto {

    @NotBlank(message = "Name cannot be empty.")
    private String name;

}
