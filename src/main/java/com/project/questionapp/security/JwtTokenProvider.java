package com.project.questionapp.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtTokenProvider {

	@Value("${questapp.app.secret}")
	private String APP_SECRET;
	@Value("${questapp.expires.in}")
	private long EXPIRES_IN;

	public String generateJwtToken(Authentication authentication) {
		JwtUserDetails userDetails = (JwtUserDetails) authentication.getPrincipal();
		Date expiredDate = new Date(new Date().getTime() + EXPIRES_IN);
		return Jwts.builder().setSubject(Integer.toString(userDetails.getId())).setIssuedAt(new Date())
				.setExpiration(expiredDate).signWith(SignatureAlgorithm.HS512, APP_SECRET).compact();
	}


	public String generateJwtTokenByUserId(int userId) {
		Date expiredDate = new Date(new Date().getTime() + EXPIRES_IN);
		return Jwts.builder().setSubject(Integer.toString(userId)).setIssuedAt(new Date())
				.setExpiration(expiredDate).signWith(SignatureAlgorithm.HS512, APP_SECRET).compact();
	
	}
	
	public int getUserIdFromJwt(String token) {
		Claims claims = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token).getBody();
		return Integer.parseInt(claims.getSubject());
	}

	boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token);
			return !isTokenExpired(token);
		} catch (SignatureException exception) {
			return false;
		} catch (MalformedJwtException exception) {
			return false;
		} catch (ExpiredJwtException exception) {
			return false;
		} catch (UnsupportedJwtException exception) {
			return false;
		} catch (IllegalArgumentException exception) {
			return false;
		}
	}

	private boolean isTokenExpired(String token) {
		Date expiration = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token).getBody().getExpiration();
		return expiration.before(new Date());
	}

}
