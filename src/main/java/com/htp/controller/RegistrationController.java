package com.htp.controller;

import com.htp.controller.requests.UserCreateRequest;
import com.htp.domain.User;
import com.htp.repository.UserRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/registration")
@RequiredArgsConstructor
public class RegistrationController {

  private final UserRepository userRepo;
  private final ConversionService conversionService;

  @ApiOperation(
      value = "Register new user", notes = "Fill all fields required ")
  @ApiResponses({
    @ApiResponse(code = 201, message = "New user is created"),
    @ApiResponse(code = 500, message = "Server error, something wrong")
  })
  @Transactional(rollbackOn = Exception.class)
  @PostMapping
  public ResponseEntity<User> registrationProcess(@RequestBody @Valid UserCreateRequest form) {
    User convertedUser = conversionService.convert(form, User.class);
    return new ResponseEntity<>(userRepo.saveAndFlush(convertedUser), HttpStatus.CREATED);
  }
}
