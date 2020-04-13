package com.htp.controller.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DesignCreateRequest {

  @NotNull
  private String shirtId;

  @NotNull
  @Size(min = 1, max = 100)
  private String text;
}
