package com.treasury.treasury.user.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.treasury.treasury.user.constants.UserRoles;
import com.treasury.treasury.user.dto.LoginDto;
import com.treasury.treasury.user.dto.LoginResponse;
import com.treasury.treasury.user.dto.UserDto;
import com.treasury.treasury.user.exceptions.UserAlreadyExistsException;
import com.treasury.treasury.user.exceptions.UserNotFoundException;
import com.treasury.treasury.user.repository.UserRepository;
import com.treasury.treasury.user.schema.User;
import com.treasury.treasury.utils.JwtUtil;

@Service
public class UserService {
  private final UserRepository userRepository;
  private final JwtUtil jwtUtil;

  public UserService(UserRepository userRepository, JwtUtil jwtUtil) {
    this.userRepository = userRepository;
    this.jwtUtil = jwtUtil;
  }

  public User create(UserDto userDto) {
    Optional<User> userAlreadyExists = this.userRepository.findByDocument(userDto.getDocument());

    if (userAlreadyExists.isPresent()) {
      throw new UserAlreadyExistsException(userDto.getDocument());
    }

    User user = new User(
        userDto.getName(),
        userDto.getEmail(),
        userDto.getDocument(),
        userDto.getRole(),
        userDto.getPassword());

    return this.userRepository.save(user);
  }

  public User getUserByDocument(String document) {
    try {
      return this.userRepository.findByDocument(document).orElseThrow(() -> new UserNotFoundException(document));
    } catch (Exception e) {
      String errorMessage = "Error on get user by document - " + e.getMessage();
      throw new RuntimeException(errorMessage);
    }
  }

  public List<User> findAllUsers() {
    try {
      List<User> users = this.userRepository.findAll();
      return users;
    } catch (Exception e) {
      String errorMessage = "Error on get all users - " + e.getMessage();
      throw new RuntimeException(errorMessage);
    }
  }

  public LoginResponse findByLogin(LoginDto loginDto) {
    try {
      User user = getUserByDocument(loginDto.getDocument());
      if (user != null && loginDto.getPassword().equals(user.getPassword()) && user.getIsActive()) {
        return this.jwtUtil.generateToken(user.getDocument(), user.getRole(), user.getName());
      }

      return null;
    } catch (Exception e) {
      String errorMessage = "Error on get users by login - " + e.getMessage();
      throw new RuntimeException(errorMessage);
    }
  }

  public User unactiveUser(String document, String loggedUserDocument, String loggedUserRole) {
    if (!UserRoles.ADMIN.toString().equals(loggedUserRole)) {
      String errorMessage = "ONLY ADMINS CAN UNACTIVE ANOTHER USER";
      throw new RuntimeException(errorMessage);
    }

    if (document.equals(loggedUserDocument)) {
      String errorMessage = "DOCUMENT AND LOGGED USER DOCUMENT MUST BE DIFERENT";
      throw new RuntimeException(errorMessage);
    }

    User user = getUserByDocument(document);
    if (user == null) {
      String errorMessage = "USER WITH DOCUMENT " + document + " NOT FOUND";
      throw new RuntimeException(errorMessage);
    }

    user.setIsActive(false);
    return this.userRepository.save(user);
  }

  public User activeUser(String document, String loggedUserDocument, String loggedUserRole) {
    if (!UserRoles.ADMIN.toString().equals(loggedUserRole)) {
      String errorMessage = "ONLY ADMINS CAN ACTIVE ANOTHER USER";
      throw new RuntimeException(errorMessage);
    }

    if (document.equals(loggedUserDocument)) {
      String errorMessage = "DOCUMENT AND LOGGED USER DOCUMENT MUST BE DIFERENT";
      throw new RuntimeException(errorMessage);
    }

    User user = getUserByDocument(document);
    if (user == null) {
      String errorMessage = "USER WITH DOCUMENT " + document + " NOT FOUND";
      throw new RuntimeException(errorMessage);
    }

    user.setIsActive(true);
    return this.userRepository.save(user);
  }

  public User update(String document, String loggedUserDocument, String loggedUserRole, UserDto userDto) {
    if (!document.equals(loggedUserDocument)) {
      String errorMessage = "USER CAN'T UPDATE ANOTHER USER DATA";
      throw new RuntimeException(errorMessage);
    }

    User user = getUserByDocument(document);

    if (user == null) {
      String errorMessage = "USER WITH DOCUMENT " + document + " NOT FOUND";
      throw new RuntimeException(errorMessage);
    }

    user.setDocument(userDto.getDocument());
    user.setName(userDto.getName());
    user.setEmail(userDto.getEmail());
    user.setPassword(userDto.getPassword());

    return this.userRepository.save(user);
  }
}
