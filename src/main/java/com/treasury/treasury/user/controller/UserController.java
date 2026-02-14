package com.treasury.treasury.user.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.treasury.treasury.user.dto.LoginDto;
import com.treasury.treasury.user.dto.LoginResponse;
import com.treasury.treasury.user.dto.UserDto;
import com.treasury.treasury.user.dto.UserResponseDto;
import com.treasury.treasury.user.schema.User;
import com.treasury.treasury.user.service.UserService;

@RequestMapping("api/user")
@RestController
@CrossOrigin(origins = "*")
class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("create")
  public ResponseEntity<User> createUser(@RequestBody UserDto userDto) {
    User user = this.userService.create(userDto);
    return new ResponseEntity<User>(user, HttpStatus.CREATED);
  }

  @PostMapping("login")
  public ResponseEntity<LoginResponse> login(@RequestBody LoginDto loginDto) {
    LoginResponse loginReponse = this.userService.findByLogin(loginDto);

    if (loginReponse != null) {
      return new ResponseEntity<LoginResponse>(loginReponse, HttpStatus.CREATED);
    }

    return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
  }

  @GetMapping("document/{document}")
  public ResponseEntity<User> getUserByDocument(@PathVariable String document) {
    User user = this.userService.getUserByDocument(document);
    return new ResponseEntity<User>(user, HttpStatus.OK);
  }

  @GetMapping("all")
  public ResponseEntity<List<UserResponseDto>> findAllUsers() {
    List<UserResponseDto> users = this.userService.findAllUsers();
    return new ResponseEntity<List<UserResponseDto>>(users, HttpStatus.OK);
  }

  @PutMapping("update/{document}")
  public ResponseEntity<User> updateUser(
      @PathVariable("document") String document,
      @RequestAttribute("loggedUserDocument") String loggedUserDocument,
      @RequestAttribute("loggedUserRole") String loggedUserRole,
      @RequestBody UserDto userDto) {
    User response = this.userService.update(document, loggedUserDocument, loggedUserRole, userDto);
    return new ResponseEntity<User>(response, HttpStatus.OK);
  }

  @PutMapping("unactive/{document}")
  public ResponseEntity<User> unactiveUser(
      @PathVariable("document") String document,
      @RequestAttribute("loggedUserDocument") String loggedUserDocument,
      @RequestAttribute("loggedUserRole") String loggedUserRole) {
    User response = this.userService.unactiveUser(document, loggedUserDocument, loggedUserRole);
    return new ResponseEntity<User>(response, HttpStatus.OK);
  }

  @PutMapping("active/{document}")
  public ResponseEntity<User> activeUser(
      @PathVariable("document") String document,
      @RequestAttribute("loggedUserDocument") String loggedUserDocument,
      @RequestAttribute("loggedUserRole") String loggedUserRole) {
    User response = this.userService.activeUser(document, loggedUserDocument, loggedUserRole);
    return new ResponseEntity<User>(response, HttpStatus.OK);
  }
}
