package com.thp.spring.simplecontext.security;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JwtGeneralAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authenticationManager;

	public JwtGeneralAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	// we want to modify the behavior when we receive the login request and we want
	// to attempt authentication

	/*
	 * Trigger when we issue (émettons) POST request to /login We also need to pass
	 * in {"username":"dan", "password":"dan123"} in the request body
	 */
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

		// Grab credentials and map them to login viewmodel
		LoginViewModel credentials = null;
		try {
			credentials = new ObjectMapper().readValue(request.getInputStream(), LoginViewModel.class);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Create login token
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				credentials.getMail(), credentials.getPassword(), new ArrayList<>());

		// Authenticate user
		Authentication auth = authenticationManager.authenticate(authenticationToken);

		return auth;
	}
	//

	// if the authentication is success we want to build the jwt token

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		// Grab principal
		// done via the database
		com.thp.spring.simplecontext.security.UserPrincipal principal = (com.thp.spring.simplecontext.security.UserPrincipal) authResult
				.getPrincipal();

		// Create JWT Token
		// HMAC512: algorithm to sign with
		String token = JWT.create().withSubject(principal.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis()
						+ com.thp.spring.simplecontext.security.JwtProperties.EXPIRATION_TIME)) // EXPIRATION_TIME :
																								// property created
																								// earlier
				.sign(HMAC512(com.thp.spring.simplecontext.security.JwtProperties.SECRET.getBytes())); // SECRET :
																										// stored in JWT
																										// properties

		// Add token in response

		// HEADER_STRING : authorization header
		// TOKEN_PREFIX : Bearer
		response.addHeader(com.thp.spring.simplecontext.security.JwtProperties.HEADER_STRING,
				com.thp.spring.simplecontext.security.JwtProperties.TOKEN_PREFIX + token);
		// response back to the user
	}

}
