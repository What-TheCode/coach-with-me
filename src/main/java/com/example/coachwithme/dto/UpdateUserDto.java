package com.example.coachwithme.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Email;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserDto {

    @Valid
    private NameDto name;
    //TODO the check for the dot does not work
    @Email(message = "Email should be valid")
    private String email;
    private String pictureUrl;

}
