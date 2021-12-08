package com.example.coachwithme.mapper;

import com.example.coachwithme.dto.UserDto;
import com.example.coachwithme.model.user.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private final NameMapper nameMapper;
    private final CoachDetailMapper coachDetailMapper;

    public UserMapper(NameMapper nameMapper,
                      CoachDetailMapper coachDetailMapper) {
        this.nameMapper = nameMapper;
        this.coachDetailMapper = coachDetailMapper;
    }

    public User toEntity(UserDto userDto) {
        return User.builder()
                .name(this.nameMapper.toEntity(userDto.getName()))
                .email(userDto.getEmail())
                .company(userDto.getCompany())
                .userRoles(userDto.getUserRoles())
                .pictureUrl(userDto.getPictureUrl())
                .password(userDto.getPassword())
                .coachDetails(this.coachDetailMapper.toEntity(userDto.getCoachDetails()))
                .build();
    }


    public UserDto toDto(User user) {
        return UserDto.builder()
                .name(this.nameMapper.toDto(user.getName()))
                .email(user.getEmail())
                .company(user.getCompany())
                .userRoles(user.getUserRoles())
                .pictureUrl(user.getPictureUrl())
                .coachDetails(this.coachDetailMapper.toDto(user.getCoachDetails()))
                .build();
    }
}
