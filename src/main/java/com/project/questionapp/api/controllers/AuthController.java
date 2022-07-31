package com.project.questionapp.api.controllers;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.questionapp.business.abstracts.RefreshTokenService;
import com.project.questionapp.business.abstracts.UserService;
import com.project.questionapp.business.constants.Messages;
import com.project.questionapp.business.requests.auth.RefreshRequest;
import com.project.questionapp.business.requests.auth.UserRequest;
import com.project.questionapp.business.responses.AuthenticationResponse;
import com.project.questionapp.entities.RefreshToken;
import com.project.questionapp.entities.User;
import com.project.questionapp.security.JwtTokenProvider;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

	private AuthenticationManager authenticationManager;
	private JwtTokenProvider jwtTokenProvider;
	private UserService userService;
	private PasswordEncoder passwordEncoder;
	private RefreshTokenService refreshTokenService;

	@Autowired
	public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider,
			UserService userService, PasswordEncoder passwordEncoder, RefreshTokenService refreshTokenService) {
		this.authenticationManager = authenticationManager;
		this.jwtTokenProvider = jwtTokenProvider;
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
		this.refreshTokenService = refreshTokenService;
	}

	@PostMapping("/login")
	public ResponseEntity<AuthenticationResponse> login(@Valid @RequestBody UserRequest loginRequest) {
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
				loginRequest.getUserName(), loginRequest.getPassword());
		Authentication auth = authenticationManager.authenticate(authToken);
		SecurityContextHolder.getContext().setAuthentication(auth);
		String jwtToken = jwtTokenProvider.generateJwtToken(auth);
		User user = this.userService.getByUserName(loginRequest.getUserName()).getData();
		AuthenticationResponse response = new AuthenticationResponse();
		response.setAccessToken("Bearer " + jwtToken);
		response.setRefreshToken(this.refreshTokenService.createRefreshToken(user));
		response.setUserId(user.getId());
		response.setMessage(Messages.tokenCreated);
		return new ResponseEntity<AuthenticationResponse>(response, HttpStatus.OK);
	}

	@PostMapping("/register")
	public ResponseEntity<AuthenticationResponse> register(@Valid @RequestBody UserRequest registerRequest)
			throws Exception {
		AuthenticationResponse response = new AuthenticationResponse();
		if (this.userService.getByUserName(registerRequest.getUserName()).getData() != null) {
			response.setMessage(Messages.userAlreadyExists);
			return new ResponseEntity<AuthenticationResponse>(response, HttpStatus.BAD_REQUEST);
		}
		User user = new User();
		user.setUserName(registerRequest.getUserName());
		user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
		user.setCreatedAt(new Date());
		this.userService.add(user);
		
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(registerRequest.getUserName(), registerRequest.getPassword());
		Authentication auth = authenticationManager.authenticate(authToken);
		SecurityContextHolder.getContext().setAuthentication(auth);
		String jwtToken = jwtTokenProvider.generateJwtToken(auth);
		
		response.setMessage(Messages.userRegistered);
		response.setAccessToken("Bearer "+jwtToken);
		response.setRefreshToken(refreshTokenService.createRefreshToken(user));
		response.setUserId(user.getId());
		return new ResponseEntity<AuthenticationResponse>(response, HttpStatus.CREATED);


	}

	@PostMapping("/refresh")
	public ResponseEntity<AuthenticationResponse> refresh(@RequestBody RefreshRequest refreshRequest) {
		AuthenticationResponse response = new AuthenticationResponse();
		RefreshToken token =  refreshTokenService.getByUser(refreshRequest.getUserId());
		
		if(token.getToken().equals(refreshRequest.getRefreshToken()) && !(this.refreshTokenService.isRefreshTokenExpired(token) )) {
			
			User user = token.getUser();


			String jwtToken = jwtTokenProvider.generateJwtTokenByUserId(user.getId());
			response.setMessage(Messages.tokenRefreshed);
			response.setAccessToken("Bearer "+jwtToken);
			response.setUserId(user.getId());
			return new ResponseEntity<AuthenticationResponse>(response, HttpStatus.OK);
			
		}
		else {
			response.setMessage(Messages.refreshTokenNotValid);
			return new ResponseEntity<AuthenticationResponse>(response, HttpStatus.UNAUTHORIZED);
		}
	}
}
