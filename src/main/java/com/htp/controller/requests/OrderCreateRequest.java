package com.htp.controller.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateRequest {
  @NotNull(message = "City is required")
  @Size(min = 3, max = 50)
  private String city;

  @NotNull(message = "Street is required")
  @Size(min = 3, max = 100)
  private String street;

  @NotNull(message = "Zip code is required")
  private String zip;

  @NotNull(message = "Card number is required")
  @Pattern(
      regexp = "(\\d{4}[ -]?){3}\\d{4}",
      message = "card number must be like '1234-1234-1234-1234'")
  private String cardNumber;

  @NotNull(message = "Card expiration date is required")
  @Pattern(
      regexp = "[0-1][0-9]/[2-9][0-9]",
      message = "card expiration date must be like '01/20'")
  private String cardExpiration;

  @NotNull(message = "Card CVV is required")
  @Pattern(regexp = "\\d{3}")
  private String cardCVV;

  @NotNull(message = "Ids of designed shirts is required")
  private Set<String> designShirtIdSet;
}
