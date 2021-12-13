package com.example.coachwithme.service;


import com.example.coachwithme.dto.CreateUserDto;
import com.example.coachwithme.dto.NameDto;
import com.example.coachwithme.dto.UpdateUserDto;
import com.example.coachwithme.exceptions.NotUniqueEmailException;
import com.example.coachwithme.mapper.CoachDetailMapper;
import com.example.coachwithme.mapper.NameMapper;
import com.example.coachwithme.mapper.UserMapper;
import com.example.coachwithme.model.user.Name;
import com.example.coachwithme.model.user.User;
import com.example.coachwithme.model.user.coach.CoachDetails;
import com.example.coachwithme.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
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
    @BeforeEach
    void setup(){
        userRepositoryMock = Mockito.mock(UserRepository.class);
        userMapperMock = Mockito.mock(UserMapper.class);
        coachDetailMapperMock = Mockito.mock(CoachDetailMapper.class);
        nameMapperMock = Mockito.mock(NameMapper.class);
        passwordEncoderMock = Mockito.mock(PasswordEncoder.class);

        userService = new UserService(userRepositoryMock,
                userMapperMock,
                coachDetailMapperMock,
                nameMapperMock,
                passwordEncoderMock);

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
    class UserServiceTestWithMockedRepo{

        @Test
        void whenRegisteringUser_thenUserMapperAndUserRepositorySaveMethodIsCalled(){

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