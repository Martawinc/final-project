package com.htp.controller.requests;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class ShirtUpdateRequest extends ShirtCreateRequest {
  @NotNull
  @NotBlank
  private String id;
}
