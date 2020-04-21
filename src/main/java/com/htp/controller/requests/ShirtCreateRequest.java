package com.htp.controller.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShirtCreateRequest {

	@NotNull
	@Size(min = 1, max = 20)
	private String id;

	@NotNull
	@Size(min = 1, max = 10)
	private String size;

	@NotNull private Long colorId;

	@PositiveOrZero private Integer quantity;

	@Positive private Float price;
}
