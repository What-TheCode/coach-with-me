package com.example.coachwithme.dto.user;


import com.example.coachwithme.dto.coachsession.topic.TopicDto;
import com.example.coachwithme.dto.user.coach.CoachDetailsDto;
import com.example.coachwithme.model.coachSession.topic.Topic;
import com.example.coachwithme.model.user.UserRole;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CoachDto {

    private int id;
    @Valid
    private NameDto name;
    private List<TopicDto> topicDto;
    @NotBlank(message = "UserRole can not be blank") // not sur if this will work
    private Set<UserRole> userRoles;
    private String pictureUrl;



}
