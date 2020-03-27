package com.htp.controller.requests;

import com.htp.domain.User;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreateRequest {

  String login;
  String password;
  String fullName;
  Date birthDate;
  String city;
  String street;
  Long zip;
  String phoneNumber;
  String mail;

  public User createUser(PasswordEncoder passwordEncoder) {
    return new User(
        login,
        passwordEncoder.encode(password),
        fullName,
        birthDate,
        city,
        street,
        zip,
        phoneNumber,
        mail);
  }
}
