package com.example.coachwithme.service;


import com.example.coachwithme.dto.user.CreateUserDto;
import com.example.coachwithme.dto.user.NameDto;
import com.example.coachwithme.dto.user.UpdateUserDto;
import com.example.coachwithme.exceptions.customExceptions.NotUniqueEmailException;
import com.example.coachwithme.mapper.user.NameMapper;
import com.example.coachwithme.mapper.user.UserMapper;
import com.example.coachwithme.mapper.user.coach.CoachDetailMapper;
import com.example.coachwithme.model.user.Name;
import com.example.coachwithme.model.user.User;
import com.example.coachwithme.model.user.coach.CoachDetails;
import com.example.coachwithme.repository.TopicRepository;
import com.example.coachwithme.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@Disabled
class UserServiceTest {

    private UserService userService;
    @Mock
    private UserRepository userRepositoryMock;
    @Mock
    private UserMapper userMapperMock;
    @Mock
    private CoachDetailMapper coachDetailMapperMock;
    @Mock
    private NameMapper nameMapperMock;
    @Mock
    private PasswordEncoder passwordEncoderMock;
    private NameDto nameDto;
    private CreateUserDto createUserDto;
    private User userEntity;
    private Name nameEntity;
    private CoachDetails coachDetailsEntity;
    private UpdateUserDto updateUserDto;
    @Mock
    private TopicRepository topicRepositoryMock;
    @Mock
    private SecurityService securityServiceMock;

    @BeforeEach
    @Disabled
    void setup() {
//        userRepositoryMock = Mockito.mock(UserRepository.class);
//        userMapperMock = Mockito.mock(UserMapper.class);
//        coachDetailMapperMock = Mockito.mock(CoachDetailMapper.class);
//        nameMapperMock = Mockito.mock(NameMapper.class);
//        passwordEncoderMock = Mockito.mock(PasswordEncoder.class);
//        topicRepositoryMock = Mockito.mock(TopicRepository.class);
//        securityServiceMock = Mockito.mock(SecurityService.class);

//        userService = new UserService(userRepositoryMock,
//                userMapperMock,
//                coachDetailMapperMock,
//                nameMapperMock,
//                passwordEncoderMock,
//                topicRepositoryMock,
//                securityServiceMock);

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

        @Test
        void whenRegisteringUserWithAUsedEmail_ThenAnExceptionIsThrown() {
            //GIVEN
            given(securityServiceMock.isaBoolean(any())).willReturn(true);
            //WHEN

            //THEN
            assertThatThrownBy(() -> userService.registerUser(createUserDto))
                    .isInstanceOf(NotUniqueEmailException.class)
                    .hasMessage("Email address already exists.");
            verify(userRepositoryMock, never()).save(any());
        }

        @Test
        @Disabled
            //TODO need to figure this out better -> wanted to do an alternative for the registering one
        void whenRegisteringUser_ThenUserIsSavedInRepository() {
            //GIVEN
            CreateUserDto newUser = createUserDto = CreateUserDto.builder()
                    .name(new NameDto("Spider", "Man"))
                    .company("Marvel")
                    .email("spider@man.org")
                    .password("supermanismyhero")
                    .pictureUrl("none")
                    .build();
            //WHEN
            userService.registerUser(newUser);
            //THEN
            ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
            verify(userRepositoryMock).save(userArgumentCaptor.capture());
            User capturedUser = userArgumentCaptor.getValue();
            assertThat(capturedUser).isEqualTo(userMapperMock.toEntity(newUser));
        }


    }


}
