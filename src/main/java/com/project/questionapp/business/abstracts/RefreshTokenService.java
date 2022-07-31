package com.project.questionapp.business.abstracts;

import com.project.questionapp.entities.RefreshToken;
import com.project.questionapp.entities.User;

public interface RefreshTokenService {

	boolean isRefreshTokenExpired(RefreshToken refreshToken);
	String createRefreshToken(User user);
	RefreshToken getByUser(int userId);
}
