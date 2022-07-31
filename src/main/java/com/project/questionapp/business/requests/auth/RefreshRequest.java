package com.project.questionapp.business.requests.auth;

import lombok.Data;

@Data
public class RefreshRequest {

	private int userId;
	private String refreshToken;
}
