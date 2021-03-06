package com.tts.UserAPI.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tts.UserAPI.model.User;
import com.tts.UserAPI.repository.UserRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;



@Api(value = "userlist")
@RestController
@RequestMapping(value = "/v2")
public class UserControllerV2 {
  @Autowired
  private UserRepository userRepository;

  @ApiOperation(value = "Get all users in the DB", response = User.class)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Successfully retrieved a user"),
      @ApiResponse(code = 400, message = "No user list retrieved without a 'State'")
  })
  @GetMapping(value = {"/", "/users"})
  public ResponseEntity<List<User>> getUsers(
      @RequestParam(value = "state", required = true) String state) {
    List<User> users = new ArrayList<>();
    if (state != null) {
      users = (List<User>) userRepository.findAllByState(state);
    } else {
      return new ResponseEntity<List<User>>(users, HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<List<User>>(users, HttpStatus.OK);
  }

  @ApiOperation(value = "Get a single user by an ID from the DB", 
      response = User.class, responseContainer = "List")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Successfully retrieved user list"),
      @ApiResponse(code = 401, message = "User was not found in the DB")
  })
  @GetMapping(value = "/users/{id}")
  public ResponseEntity<Optional<User>> getUserById(@PathVariable(value = "id") Long id) {
    Optional<User> user = userRepository.findById(id);
    if (!user.isPresent()) {
      return new ResponseEntity<Optional<User>>(user, HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<Optional<User>>(user, HttpStatus.OK);
  }

  @ApiOperation(value = "Create a new user and add to DB", response = Void.class)
  @ApiResponses(value = {
      @ApiResponse(code = 201, message = "Successfully created a new user in DB"),
      @ApiResponse(code = 400, message = "Invalid user input, user was not created")
  })
  @PostMapping(value = "/users")
  public ResponseEntity<Void> createUser(@RequestBody @Valid User user,
      BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
    }
    userRepository.save(user);
    return new ResponseEntity<Void>(HttpStatus.CREATED);
  }

  @ApiOperation(value = "Edit a user found and replaced in the DB", response = Void.class)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Successfully edited a user in DB"),
      @ApiResponse(code = 400, message = "Invalid user change input, user was not edited"),
      @ApiResponse(code = 404, message = "User to edit was not found in the DB")
  })
  @PutMapping(value = "/users/{id}")
  public ResponseEntity<Void> updateUser(
      @PathVariable(value = "id") Long id, @RequestBody @Valid User user,
      BindingResult bindingResult) {
    Optional<User> updateUser = userRepository.findById(id);
    if (!updateUser.isPresent()) {
      return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
    }
    if (bindingResult.hasErrors()) {
      return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
    }
    userRepository.save(user);
    return new ResponseEntity<Void>(HttpStatus.OK);
  }

  @ApiOperation(value = "Delete a user found in the DB", response = Void.class)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Successfully deleted a user from DB"),
      @ApiResponse(code = 404, message = "User to delete was not found in the DB")
  })
  @DeleteMapping(value = "/users/{id}")
  public ResponseEntity<Void> deleteById(@PathVariable(value = "id") Long id) {
    Optional<User> userToDelete = userRepository.findById(id);
    if (!userToDelete.isPresent()) {
      return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
    }
    userRepository.deleteById(id);
    return new ResponseEntity<Void>(HttpStatus.OK);
  }
  
}
