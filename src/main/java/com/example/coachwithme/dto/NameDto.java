package com.example.coachwithme.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Builder
@NoArgsConstructor
@Getter
public class NameDto {
    @NotBlank(message = "Firstname can not be empty")
    private String firstName;
    @NotBlank(message = "Lastname can not be empty")
    private String lastName;
}
