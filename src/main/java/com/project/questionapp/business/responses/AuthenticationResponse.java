package com.project.questionapp.business.responses;

import lombok.Data;

@Data
public class AuthenticationResponse {

	private int userId;
	private String message;
	private String accessToken;
	private String refreshToken;
}
