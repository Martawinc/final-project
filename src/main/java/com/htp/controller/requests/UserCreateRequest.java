package com.htp.controller.requests;

import com.htp.domain.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.util.Date;

@Data
@NoArgsConstructor
public class UserCreateRequest {

  @NotBlank(message = "Login is required")
  private String login;

  @NotBlank(message = "Password is required")
  private String password;

  @NotBlank(message = "FullName is required")
  private String fullName;

  @Past(message = "Birth date is required")
  private Date birthDate;

  @NotBlank(message = "City is required")
  private String city;

  @NotBlank(message = "Street is required")
  private String street;

  @NotBlank(message = "Zip code is required")
  private Long zip;

  @NotBlank(message = "Phone number is required")
  private String phoneNumber;

  @Email(message = "Not a valid e-mail")
  private String mail;

  public User createUser(PasswordEncoder passwordEncoder) {
    User user = new User();
    user.setLogin(login);
    user.setPassword(passwordEncoder.encode(password));
    user.setFullName(fullName);
    user.setBirthDate(birthDate);
    user.setCity(city);
    user.setStreet(street);
    user.setZip(zip);
    user.setPhoneNumber(phoneNumber);
    user.setMail(mail);
    return user;
  }
}
