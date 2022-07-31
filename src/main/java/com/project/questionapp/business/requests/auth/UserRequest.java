package com.project.questionapp.business.requests.auth;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.sun.istack.NotNull;

import lombok.Data;

@Data
public class UserRequest {
	
	@NotNull
	@NotBlank(message = "Username is not allowed to be empty")
	private String userName;
	
	@NotNull
	@NotBlank(message = "Password is not allowed to be empty")
	@Size(min = 4, message = "Password must contain at least 4 characters")
	private String password;

}
