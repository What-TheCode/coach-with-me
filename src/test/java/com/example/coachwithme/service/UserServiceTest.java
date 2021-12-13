package com.example.coachwithme.service;


import com.example.coachwithme.dto.user.CreateUserDto;
import com.example.coachwithme.dto.user.NameDto;
import com.example.coachwithme.dto.user.UpdateUserDto;
import com.example.coachwithme.mapper.user.NameMapper;
import com.example.coachwithme.mapper.user.UserMapper;
import com.example.coachwithme.mapper.user.coach.CoachDetailMapper;
import com.example.coachwithme.model.user.Name;
import com.example.coachwithme.model.user.User;
import com.example.coachwithme.model.user.coach.CoachDetails;
import com.example.coachwithme.repository.TopicRepository;
import com.example.coachwithme.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

class UserServiceTest {

    private UserService userService;
    private UserRepository userRepositoryMock;
    private UserMapper userMapperMock;
    private CoachDetailMapper coachDetailMapperMock;
    private NameMapper nameMapperMock;
    private PasswordEncoder passwordEncoderMock;
    private NameDto nameDto;
    private CreateUserDto createUserDto;
    private User userEntity;
    private Name nameEntity;
    private CoachDetails coachDetailsEntity;
    private UpdateUserDto updateUserDto;
    private TopicRepository topicRepository;
    private SecurityService securityService;

    @BeforeEach
    void setup() {
        userRepositoryMock = Mockito.mock(UserRepository.class);
        userMapperMock = Mockito.mock(UserMapper.class);
        coachDetailMapperMock = Mockito.mock(CoachDetailMapper.class);
        nameMapperMock = Mockito.mock(NameMapper.class);
        passwordEncoderMock = Mockito.mock(PasswordEncoder.class);
        topicRepository = Mockito.mock(TopicRepository.class);
        securityService = Mockito.mock(SecurityService.class);

        userService = new UserService(userRepositoryMock,
                userMapperMock,
                coachDetailMapperMock,
                nameMapperMock,
                passwordEncoderMock,
                topicRepository,
                securityService);

        nameDto = NameDto.builder()
                .firstName("Super")
                .lastName("Man")
                .build();

        createUserDto = CreateUserDto.builder()
                .name(nameDto)
                .company("DC corp")
                .email("super@man.org")
                .password("spidermanismyhero")
                .pictureUrl("none")
                .build();

        nameEntity = Name.builder()
                .firstName("Super")
                .lastName("Man")
                .build();

        coachDetailsEntity = CoachDetails.builder()
                .build();

        userEntity = User.builder()
                .name(nameEntity)
                .company("DC corp")
                .email("super@man.org")
                .pictureUrl("none")
                .coachDetails(coachDetailsEntity)
                .build();

        updateUserDto = UpdateUserDto.builder()
                .name(nameDto)
                .email("super@man.org")
                .pictureUrl("none")
                .build();
    }

    @DisplayName("UserService Test with Mocked Repository")
    @Nested
    class UserServiceTestWithMockedRepo {

        @Test
        void whenRegisteringUser_thenUserMapperAndUserRepositorySaveMethodIsCalled() {

            Mockito.when(userMapperMock.toEntity(createUserDto))
                    .thenReturn(userEntity);

            userService.registerUser(createUserDto);

            InOrder expectedExecutionFlow = Mockito
                    .inOrder(userMapperMock,
                            userRepositoryMock);

            expectedExecutionFlow.verify(userMapperMock).toEntity(createUserDto);
            expectedExecutionFlow.verify(userRepositoryMock).save(userEntity);

        }


    }


}