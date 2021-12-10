package com.example.coachwithme.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserDto {

    @Valid
    private NameDto name;
    @Email(message = "Email should be valid")
    private String email;
    @NotBlank(message = "Company can not be empty")
    private String company;

    private String pictureUrl;
    @NotBlank(message = "Password can not be empty")
    private String password;


}
