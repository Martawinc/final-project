package com.htp.controller;

import com.htp.domain.User;
import com.htp.repository.UserRepository;
import com.htp.security.Headers;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping(value = "/rest/users")
@RequiredArgsConstructor
public class UserController {

  private final UserRepository userRepo;

  @GetMapping("/all")
  @ApiImplicitParams({
    @ApiImplicitParam(name = Headers.AUTH_TOKEN, value = "token", required = true, dataType = "string", paramType = "header" )
  })
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<List<User>> getAllUsers() {
    return new ResponseEntity<>(userRepo.findAll(), HttpStatus.OK);
  }

  @ApiOperation(value = "Find user by id")
  @ApiResponses({
    @ApiResponse(code = 200, message = "Successful getting user"),
    @ApiResponse(code = 404, message = "User with such id not found"),
    @ApiResponse(code = 500, message = "Server error, something wrong")
  })
  @GetMapping("/{id}")
  public ResponseEntity<User> userById(
      @ApiParam(value = "Id of user that need to be found") @PathVariable("id") Long id) {
    Optional<User> userById = userRepo.findById(id);
    return userById
        .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
        .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
  }
}
