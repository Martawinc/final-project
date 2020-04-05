package com.htp.controller.requests;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.util.Date;

@Data
@NoArgsConstructor
public class UserCreateRequest {

  @NotEmpty(message = "Login is required")
  private String username;

  @NotEmpty(message = "Password is required")
  private String password;

  @NotEmpty(message = "FullName is required")
  private String fullName;

  @Past(message = "Birth date is required")
  private Date birthDate;

  @NotEmpty(message = "City is required")
  private String city;

  @NotEmpty(message = "Street is required")
  private String street;

  @NotNull(message = "Zip code is required")
  private Long zip;

  @NotEmpty(message = "Phone number is required")
  private String phoneNumber;

  @Email(message = "Not a valid e-mail")
  private String mail;
}
