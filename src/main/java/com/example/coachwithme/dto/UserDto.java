package com.example.coachwithme.dto;

import com.example.coachwithme.model.user.UserRole;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
public class UserDto {

    private int id;
    @Valid
    private NameDto name;
    @Email(message = "Email should be valid")
    private String email;
    @NotBlank(message = "Company can not be empty")
    private String company;
    @NotBlank(message ="UserRole can not be blank") // not sur if this will work
    private List<UserRole> userRoles;
    private String pictureUrl;
    @NotBlank(message = "Password can not be empty")
    @Pattern(regexp = "")
    private String password;
    private CoachDetailsDto coachDetails;


}
