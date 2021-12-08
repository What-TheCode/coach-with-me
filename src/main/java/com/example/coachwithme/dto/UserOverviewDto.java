package com.example.coachwithme.dto;

import com.example.coachwithme.model.user.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserOverviewDto {
    private int id;
    @Valid
    private NameDto name;
    @Email(message = "Email should be valid")
    private String email;
    private String company;
    private List<UserRole> userRoles;
    private String pictureUrl;
    private CoachDetailsDto coachDetails;
}
