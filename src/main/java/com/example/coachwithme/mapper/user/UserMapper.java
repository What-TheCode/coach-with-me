package com.example.coachwithme.mapper.user;

import com.example.coachwithme.dto.user.CreateUserDto;
import com.example.coachwithme.dto.user.UserDto;
import com.example.coachwithme.mapper.user.coach.CoachDetailMapper;
import com.example.coachwithme.model.user.User;
import com.example.coachwithme.model.user.UserRole;
import org.springframework.stereotype.Component;

import java.util.Set;

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
                .coachDetails(this.coachDetailMapper.toEntity(userDto.getCoachDetails()))
                .build();
    }

    public User toEntity(CreateUserDto createUserDto) {
        return User.builder()
                .name(this.nameMapper.toEntity(createUserDto.getName()))
                .email(createUserDto.getEmail())
                .company(createUserDto.getCompany())
                .userRoles(Set.of(UserRole.COACHEE))
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
                .coachDetails(this.coachDetailMapper.toDto(user.getCoachDetails()))
                .build();
    }
}
