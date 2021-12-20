//package com.example.coachwithme.service;
//
//
//import com.example.coachwithme.dto.user.CreateUserDto;
//import com.example.coachwithme.dto.user.NameDto;
//import com.example.coachwithme.dto.user.UpdateUserDto;
//import com.example.coachwithme.dto.user.UserDto;
//import com.example.coachwithme.exceptions.customExceptions.NotUniqueEmailException;
//import com.example.coachwithme.exceptions.customExceptions.UserDoesNotExistException;
//import com.example.coachwithme.mapper.user.NameMapper;
//import com.example.coachwithme.mapper.user.UserMapper;
//import com.example.coachwithme.mapper.user.coach.CoachDetailMapper;
//import com.example.coachwithme.model.user.Name;
//import com.example.coachwithme.model.user.User;
//import com.example.coachwithme.model.user.UserRole;
//import com.example.coachwithme.model.user.coach.CoachDetails;
//import com.example.coachwithme.repository.TopicRepository;
//import com.example.coachwithme.repository.UserRepository;
//import org.aspectj.weaver.NameMangler;
//import org.junit.jupiter.api.*;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.ArgumentCaptor;
//import org.mockito.InOrder;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import javax.naming.NoPermissionException;
//import java.util.Optional;
//import java.util.Set;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//@DataJpaTest
//class UserServiceTest {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    private UserService userServiceMock;
//
//    @Mock
//    private UserRepository userRepositoryMock;
//
//    @Mock
//    private UserMapper userMapperMock;
//
//    @Mock
//    private CoachDetailMapper coachDetailMapperMock;
//
//    @Mock
//    private NameMapper nameMapperMock;
//
//    @Mock
//    private PasswordEncoder passwordEncoderMock;
//
//    @Mock
//    private TopicRepository topicRepositoryMock;
//
//    @Mock
//    private SecurityService securityServiceMock;
//
//    private NameDto nameDto;
//
//    private CreateUserDto createUserDto;
//
//    private User userEntity;
//
//    private Name nameEntity;
//
//    private CoachDetails coachDetailsEntity;
//
//    private UpdateUserDto updateUserDto;
//
//    private UserDto userDto;
//
//
//    @BeforeEach
//    void setup() {
////
////        userRepositoryMock = Mockito.mock(UserRepository.class);
////        userMapperMock = Mockito.mock(UserMapper.class);
////        coachDetailMapperMock = Mockito.mock(CoachDetailMapper.class);
////        nameMapperMock = Mockito.mock(NameMapper.class);
////        passwordEncoderMock = Mockito.mock(PasswordEncoder.class);
////        topicRepositoryMock = Mockito.mock(TopicRepository.class);
////        securityServiceMock = Mockito.mock(SecurityService.class);
//
//        userServiceMock = new UserService(userRepositoryMock,
//                userMapperMock,
//                nameMapperMock,
//                passwordEncoderMock,
//                securityServiceMock);
//
//        nameDto = NameDto.builder()
//                .firstName("Super")
//                .lastName("Man")
//                .build();
//
//        createUserDto = CreateUserDto.builder()
//                .name(nameDto)
//                .company("DC corp")
//                .email("super@man.org")
//                .password("spidermanismyhero")
//                .pictureUrl("none")
//                .build();
//
//        nameEntity = Name.builder()
//                .firstName("Super")
//                .lastName("Man")
//                .build();
//
//        coachDetailsEntity = CoachDetails.builder()
//                .build();
//
//        userEntity = User.builder()
//                .name(nameEntity)
//                .company("DC corp")
//                .email("super@man.org")
//                .pictureUrl("none")
//                .coachDetails(coachDetailsEntity)
//                .build();
//
//        updateUserDto = UpdateUserDto.builder()
//                .name(nameDto)
//                .email("super@man.org")
//                .pictureUrl("none")
//                .build();
//
//        userDto = UserDto.builder()
//                .id(1)
//                .name(NameDto.builder()
//                        .firstName("Alex")
//                        .lastName("V")
//                        .build())
//                .email("alex@hotmail.com")
//                .company("Switchfully")
//                .userRoles(Set.of(UserRole.COACHEE, UserRole.COACH))
//                .pictureUrl("URL")
//                .coachDetails(null)
//                .build();
//    }
//
//    @DisplayName("UserService Test with Mocked Repository")
//    @Nested
//    class UserServiceTestWithMockedRepo {
//
//        @Test
//        void whenRegisteringUser_thenUserMapperAndUserRepositorySaveMethodIsCalled() {
//
//            Mockito.when(userMapperMock.toEntity(createUserDto))
//                    .thenReturn(userEntity);
//
//            userServiceMock.registerUser(createUserDto);
//
//            InOrder expectedExecutionFlow = Mockito
//                    .inOrder(userMapperMock,
//                            userRepositoryMock);
//
//            expectedExecutionFlow.verify(userMapperMock).toEntity(createUserDto);
//            expectedExecutionFlow.verify(userRepositoryMock).save(userEntity);
//
//        }
//
//        @Test
//        void whenRegisteringUser_thenTheEmailIsAsserted() {
//            given(userMapperMock.toEntity(createUserDto)).willReturn(userEntity);
//            userServiceMock.registerUser(createUserDto);
//            Mockito.verify(securityServiceMock).assertIfTheEmailIsExisting("super@man.org");
//
//        }
//
//        @Test
//        @Disabled
//        void whenRegisteringUserWithAUsedEmail_ThenAnExceptionIsThrown() {
//            //GIVEN
//            String emailInDatabase ="super@man.org";
//
//
//            userRepository.save(userEntity);
//            given(userMapperMock.toEntity(createUserDto)).willReturn(userEntity);
////            given(userRepositoryMock.findByEmail(emailInDatabase)).willReturn(userEntity);
//
//            //WHEN
//
//            //THEN
//            assertThatThrownBy(() -> userServiceMock.registerUser(createUserDto))
//                    .isInstanceOf(NotUniqueEmailException.class)
//                    .hasMessage("400 BAD_REQUEST \"Email address already exists.\"");
//            verify(userRepositoryMock, never()).save(any());
//        }
//
//        @Test
//        @Disabled
//            //TODO need to figure this out better -> wanted to do an alternative for the registering one
//        void whenRegisteringUser_ThenUserIsSavedInRepository() {
//            //GIVEN
//            CreateUserDto newUser = createUserDto = CreateUserDto.builder()
//                    .name(new NameDto("Spider", "Man"))
//                    .company("Marvel")
//                    .email("spider@man.org")
//                    .password("supermanismyhero")
//                    .pictureUrl("none")
//                    .build();
//            //WHEN
//            userServiceMock.registerUser(newUser);
//            //THEN
//            ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
//            verify(userRepositoryMock).save(userArgumentCaptor.capture());
//            User capturedUser = userArgumentCaptor.getValue();
//            assertThat(capturedUser).isEqualTo(userMapperMock.toEntity(newUser));
//        }
//
//
//    }
//
//    @Nested
//    @DisplayName("When user is fetched from repository based on userId")
//    class WhenExistingUserIsFetchedFromRepository_ThenReturnUserDto {
//
//        @Test
//        @Disabled
//        @DisplayName("Then exception is thrown")
//        void whenShowUserProfileInfo() {
////            Optional<User> user = Optional.of(userEntity);
////            given(userRepositoryMock.findById(1)).willReturn(user);
//            Mockito.when(userServiceMock.showUserProfileInfo(1))
//                    .thenReturn(userDto);
//
//            assertThatThrownBy(() -> userServiceMock.showUserProfileInfo(1000))
//                    .isInstanceOf(UserDoesNotExistException.class)
//                    .hasMessage("user with id 1 does not exist");
//            verify(userRepositoryMock, never()).save(any());
//        }
//
////        @Test
////        @DisplayName("Then correct userDto is returned")
////        void whenShowUserProfileInfo() {
////            Optional<User> user = Optional.of(userEntity);
////            given(userRepositoryMock.findById(1)).willReturn(user);
////            Mockito.when(userServiceMock.showUserProfileInfo(1))
////                    .thenReturn(userDto);
////
////            Mockito.verify(userServiceMock, times(1)).showUserProfileInfo(1);
////            Mockito.verify(userMapperMock, times(1)).toDto(Mockito.any());
////        }
//
//
////        @Test
////        @DisplayName("Then correct userDto is returned")
////        void whenShowUserFrofileInfo() {
////            Mockito.when(userService.showUserProfileInfo(1))
////                    .thenReturn(userDto);
////
////            Mockito.verify()
////        }
//
//    }
//
//
//}