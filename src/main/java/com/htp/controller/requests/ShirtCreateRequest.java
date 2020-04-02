package com.htp.controller.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShirtCreateRequest {

  @NotNull private String id;

  @NotNull private String size;

  @NotNull private Long colorId;

  @PositiveOrZero private Integer quantity;

  @Positive private Float price;
}
