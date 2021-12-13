package com.example.coachwithme.dto.user;

import com.example.coachwithme.dto.user.coach.CoachDetailsDto;
import com.example.coachwithme.model.user.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private int id;

    @Valid
    private NameDto name;

    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Company can not be empty")
    private String company;

    @NotBlank(message = "UserRole can not be blank") // not sur if this will work
    private Set<UserRole> userRoles;

    private String pictureUrl;

    private CoachDetailsDto coachDetails;


}
