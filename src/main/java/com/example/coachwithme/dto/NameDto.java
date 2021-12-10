package com.example.coachwithme.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class NameDto {
    @NotBlank(message = "Firstname can not be empty")
    private String firstName;
    @NotBlank(message = "Lastname can not be empty")
    private String lastName;

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}
