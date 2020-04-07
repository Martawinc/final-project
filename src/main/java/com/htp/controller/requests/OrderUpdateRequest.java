package com.htp.controller.requests;

import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class OrderUpdateRequest extends OrderCreateRequest {
  @NotNull private Long id;
}
