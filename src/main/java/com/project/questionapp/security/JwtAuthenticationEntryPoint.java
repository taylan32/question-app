package com.project.questionapp.security;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;



@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		if(authException.getMessage().equals("Bad credentials")) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Username or password is incorrect.");
		}
		else {			
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED,  authException.getMessage());
		}
	}

}