package com.htp.controller.requests;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Request for user authentication")
public class AuthRequest implements Serializable {

	@NotEmpty
	@ApiModelProperty(required = true, dataType = "String", notes = "Unique user's  login")
	private String username;

	@NotEmpty
	@ApiModelProperty(required = true, dataType = "String")
	private String password;
}
