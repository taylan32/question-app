package com.project.questionapp.business.concretes;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.project.questionapp.business.abstracts.RefreshTokenService;
import com.project.questionapp.dataAccess.RefreshTokenDao;
import com.project.questionapp.entities.RefreshToken;
import com.project.questionapp.entities.User;


@Service
public class RefreshTokenManager implements RefreshTokenService {

	private RefreshTokenDao refreshTokenDao;
	@Value("${refresh.token.expires.in}")
	int expireSeconds;
	
	@Autowired
	public RefreshTokenManager(RefreshTokenDao refreshTokenDao) {
		this.refreshTokenDao = refreshTokenDao;
	}
	@Override
	public boolean isRefreshTokenExpired(RefreshToken refreshToken) {
		return refreshToken.getExpiryDate().before(new Date());
	}
	@Override
	public String createRefreshToken(User user) {
		RefreshToken token = new RefreshToken();
		token.setUser(user);
		token.setToken(UUID.randomUUID().toString());
		token.setExpiryDate(Date.from(Instant.now().plusSeconds(expireSeconds)));
		refreshTokenDao.save(token);
		return token.getToken();
	}
	@Override
	public RefreshToken getByUser(int userId) {
		System.out.println(this.refreshTokenDao.getByUser_Id(userId));
		return this.refreshTokenDao.getByUser_Id(userId);
	}

}
