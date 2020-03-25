package com.htp.controller.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateRequest {

    private String login;
    private String password;
    private String fullName;
    private Date birthDate;
    private String city;
    private String street;
    private Long zip;
    private String phoneNumber;
    private String mail;
}
