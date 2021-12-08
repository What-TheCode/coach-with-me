package com.example.coachwithme.mapper;

import com.example.coachwithme.dto.CreateUserDto;
import com.example.coachwithme.dto.UserDto;
import com.example.coachwithme.model.user.User;
import com.example.coachwithme.model.user.coach.CoachDetails;
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
                .id(userDto.getId())
                .name(this.nameMapper.toEntity(userDto.getName()))
                .email(userDto.getEmail())
                .company(userDto.getCompany())
                .userRoles(userDto.getUserRoles())
                .pictureUrl(userDto.getPictureUrl())
                .password(userDto.getPassword())
                .coachDetails(this.coachDetailMapper.toEntity(userDto.getCoachDetails()))
                .build();
    }

    public User toEntity(CreateUserDto createUserDto) {
        return User.builder()
                .name(this.nameMapper.toEntity(createUserDto.getName()))
                .email(createUserDto.getEmail())
                .company(createUserDto.getCompany())
                .userRoles(createUserDto.getUserRoles())
                .pictureUrl(createUserDto.getPictureUrl())
                .password(createUserDto.getPassword())
                .build();
    }


    public UserDto toDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(this.nameMapper.toDto(user.getName()))
                .email(user.getEmail())
                .company(user.getCompany())
                .userRoles(user.getUserRoles())
                .pictureUrl(user.getPictureUrl())
                .password(user.getPassword())
                .coachDetails(this.coachDetailMapper.toDto(user.getCoachDetails()))
                .build();
    }
}
