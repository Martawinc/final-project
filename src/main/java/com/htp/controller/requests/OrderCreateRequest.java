package com.htp.controller.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateRequest {
  @NotNull private String city;

  @NotNull private String street;

  @NotNull private String zip;

  @NotNull private String cardNumber;

  @NotNull private String cardExpiration;

  @NotNull private String cardCVV;

  @NotNull private String userId;

  @NotNull private Set<String> designShirtIdSet;
}
