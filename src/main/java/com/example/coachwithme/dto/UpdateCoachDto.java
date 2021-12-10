package com.example.coachwithme.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCoachDto {

    @NotBlank(message = "Introduction cannot be empty.")
    private String coachIntroduction;

    @NotBlank(message = "Availability cannot be empty.")
    private String coachAvailability;

}
