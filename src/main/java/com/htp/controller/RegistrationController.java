package com.htp.controller;

import com.htp.controller.requests.UserCreateRequest;
import com.htp.domain.User;
import com.htp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/registration")
@RequiredArgsConstructor
public class RegistrationController {

  private final UserRepository userRepo;
  private final ConversionService conversionService;

  @PostMapping
  @Transactional
  public ResponseEntity<User> registrationProcess(@RequestBody @Valid UserCreateRequest form) {
    User convertedUser = conversionService.convert(form, User.class);
    return new ResponseEntity<>(userRepo.saveAndFlush(convertedUser), HttpStatus.CREATED);
  }
}
