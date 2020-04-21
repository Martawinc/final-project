package com.htp.controller.requests;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@NoArgsConstructor
public class UserCreateRequest {

	@NotEmpty(message = "Login is required")
	@Pattern(regexp = "\\w{8,20}", message = "username must contain letters and/or numbers")
	private String username;

	@NotEmpty(message = "Password is required")
	@Pattern(regexp = "\\w{8,20}", message = "password must contain letters and/or numbers")
	private String password;

	@NotEmpty(message = "FullName is required")
	@Size(min = 3, max = 100)
	private String fullName;

	@Past(message = "Birth date is required")
	private Date birthDate;

	@NotEmpty(message = "City is required")
	@Size(min = 3, max = 100)
	private String city;

	@NotEmpty(message = "Street is required")
	@Size(min = 3, max = 100)
	private String street;

	@NotNull(message = "Zip code is required")
	private Long zip;

	@NotEmpty(message = "Phone number is required")
	@Pattern(
			regexp = "\\+375([\\s()-]?\\d){9}",
			message = "phone number must start with '+375' and have code")
	private String phoneNumber;

	@Email(message = "Not a valid e-mail")
	@Size(max = 100)
	private String mail;
}
