package com.treasury.treasury.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.treasury.treasury.user.constants.UserRoles;
import com.treasury.treasury.user.dto.LoginDto;
import com.treasury.treasury.user.dto.LoginResponse;
import com.treasury.treasury.user.dto.UserDto;
import com.treasury.treasury.user.dto.UserResponseDto;
import com.treasury.treasury.user.exceptions.UserAlreadyExistsException;
import com.treasury.treasury.user.exceptions.UserNotFoundException;
import com.treasury.treasury.user.repository.UserRepository;
import com.treasury.treasury.user.schema.User;
import com.treasury.treasury.user.service.UserService;
import com.treasury.treasury.utils.JwtUtil;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

  @Mock
  private UserRepository userRepository;

  @Mock
  private JwtUtil jwtUtil;

  @InjectMocks
  private UserService userService;

  private User sampleUser;
  private UserDto sampleUserDto;

  @BeforeEach
  void setUp() {
    sampleUser = new User("John Doe", "john@example.com", "123456789", UserRoles.COMMON, "password123");
    sampleUserDto = new UserDto("John Doe", "john@example.com", "123456789", UserRoles.COMMON, "password123");
  }

  @Test
  void shouldCreateUserSuccessfully() {
    when(userRepository.findByDocument(sampleUserDto.getDocument())).thenReturn(Optional.empty());
    when(userRepository.save(any(User.class))).thenReturn(sampleUser);

    User createdUser = userService.create(sampleUserDto);

    assertNotNull(createdUser);
    assertEquals(sampleUserDto.getDocument(), createdUser.getDocument());
    verify(userRepository, times(1)).save(any(User.class));
  }

  @Test
  void shouldThrowExceptionWhenUserAlreadyExists() {
    when(userRepository.findByDocument(sampleUserDto.getDocument())).thenReturn(Optional.of(sampleUser));

    assertThrows(UserAlreadyExistsException.class, () -> userService.create(sampleUserDto));
    verify(userRepository, times(0)).save(any(User.class));
  }

  @Test
  void shouldGetUserByDocumentSuccessfully() {
    when(userRepository.findByDocument("123456789")).thenReturn(Optional.of(sampleUser));

    User foundUser = userService.getUserByDocument("123456789");

    assertNotNull(foundUser);
    assertEquals("123456789", foundUser.getDocument());
  }

  @Test
  void shouldThrowExceptionWhenUserNotFoundByDocument() {
    when(userRepository.findByDocument("999")).thenReturn(Optional.empty());

    assertThrows(RuntimeException.class, () -> userService.getUserByDocument("999"));
  }

  @Test
  void shouldFindAllUsersSuccessfully() {
    User user2 = new User("Jane Doe", "jane@example.com", "987654321", UserRoles.ADMIN, "password456");
    when(userRepository.findAll()).thenReturn(Arrays.asList(sampleUser, user2));

    List<UserResponseDto> users = userService.findAllUsers();

    assertEquals(2, users.size());
    assertEquals("John Doe", users.get(0).name());
    assertEquals("Jane Doe", users.get(1).name());
  }

  @Test
  void shouldLoginSuccessfully() {
    LoginDto loginDto = new LoginDto("123456789", "password123");
    LoginResponse expectedResponse = new LoginResponse("mocked-token");

    when(userRepository.findByDocument("123456789")).thenReturn(Optional.of(sampleUser));
    when(jwtUtil.generateToken(sampleUser.getDocument(), sampleUser.getRole(), sampleUser.getName()))
        .thenReturn(expectedResponse);

    LoginResponse response = userService.findByLogin(loginDto);

    assertNotNull(response);
    assertEquals("mocked-token", response.getToken());
  }

  @Test
  void shouldReturnNullOnLoginWithWrongPassword() {
    LoginDto loginDto = new LoginDto("123456789", "wrong-password");
    when(userRepository.findByDocument("123456789")).thenReturn(Optional.of(sampleUser));

    LoginResponse response = userService.findByLogin(loginDto);

    assertNull(response);
  }

  @Test
  void shouldUnactiveUserSuccessfully() {
    User targetUser = new User("Target", "target@example.com", "000", UserRoles.COMMON, "pass");
    when(userRepository.findByDocument("000")).thenReturn(Optional.of(targetUser));
    when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

    User result = userService.unactiveUser("000", "123", UserRoles.ADMIN.toString());

    assertNotNull(result);
    assertEquals(false, result.getIsActive());
    verify(userRepository, times(1)).save(targetUser);
  }

  @Test
  void shouldThrowExceptionWhenNonAdminTriesToUnactiveUser() {
    assertThrows(RuntimeException.class, () -> 
        userService.unactiveUser("000", "123", UserRoles.COMMON.toString()));
  }

  @Test
  void shouldThrowExceptionWhenAdminTriesToUnactiveThemselves() {
    assertThrows(RuntimeException.class, () -> 
        userService.unactiveUser("123", "123", UserRoles.ADMIN.toString()));
  }

  @Test
  void shouldActiveUserSuccessfully() {
    User targetUser = new User("Target", "target@example.com", "000", UserRoles.COMMON, "pass");
    targetUser.setIsActive(false);
    when(userRepository.findByDocument("000")).thenReturn(Optional.of(targetUser));
    when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

    User result = userService.activeUser("000", "123", UserRoles.ADMIN.toString());

    assertNotNull(result);
    assertEquals(true, result.getIsActive());
    verify(userRepository, times(1)).save(targetUser);
  }

  @Test
  void shouldUpdateUserSuccessfully() {
    when(userRepository.findByDocument("123456789")).thenReturn(Optional.of(sampleUser));
    when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

    UserDto updateDto = new UserDto("John Updated", "updated@example.com", "123456789", UserRoles.COMMON, "newpass");
    User result = userService.update("123456789", "123456789", UserRoles.COMMON.toString(), updateDto);

    assertNotNull(result);
    assertEquals("John Updated", result.getName());
    assertEquals("updated@example.com", result.getEmail());
    assertEquals("newpass", result.getPassword());
  }

  @Test
  void shouldThrowExceptionWhenUpdatingAnotherUser() {
    UserDto updateDto = new UserDto("John Updated", "updated@example.com", "123456789", UserRoles.COMMON, "newpass");
    assertThrows(RuntimeException.class, () -> 
        userService.update("123456789", "987654321", UserRoles.COMMON.toString(), updateDto));
  }

  @Test
  void shouldThrowExceptionWhenNonAdminTriesToActiveUser() {
    assertThrows(RuntimeException.class, () -> 
        userService.activeUser("000", "123", UserRoles.COMMON.toString()));
  }

  @Test
  void shouldThrowExceptionWhenAdminTriesToActiveThemselves() {
    assertThrows(RuntimeException.class, () -> 
        userService.activeUser("123", "123", UserRoles.ADMIN.toString()));
  }

  @Test
  void shouldThrowRuntimeExceptionWhenRepositoryFailsOnGetUserByDocument() {
    when(userRepository.findByDocument(any())).thenThrow(new RuntimeException("DB Error"));

    RuntimeException exception = assertThrows(RuntimeException.class, () -> 
        userService.getUserByDocument("123"));
    
    assertEquals("Error on get user by document - DB Error", exception.getMessage());
  }

  @Test
  void shouldThrowRuntimeExceptionWhenRepositoryFailsOnFindAllUsers() {
    when(userRepository.findAll()).thenThrow(new RuntimeException("DB Error"));

    RuntimeException exception = assertThrows(RuntimeException.class, () -> 
        userService.findAllUsers());
    
    assertEquals("Error on get all users - DB Error", exception.getMessage());
  }

  @Test
  void shouldThrowRuntimeExceptionWhenRepositoryFailsOnFindByLogin() {
    LoginDto loginDto = new LoginDto("123", "pass");
    when(userRepository.findByDocument(any())).thenThrow(new RuntimeException("DB Error"));

    RuntimeException exception = assertThrows(RuntimeException.class, () -> 
        userService.findByLogin(loginDto));
    
    assertEquals("Error on get users by login - Error on get user by document - DB Error", exception.getMessage());
  }
}
