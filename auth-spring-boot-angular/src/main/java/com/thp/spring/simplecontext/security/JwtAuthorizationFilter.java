package com.thp.spring.simplecontext.security;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.thp.spring.simplecontext.entity.User;
import com.thp.spring.simplecontext.repository.UserRepository;
import com.thp.spring.simplecontext.service.UserService;
import com.thp.spring.simplecontext.serviceImpl.UserServiceImpl;

@Transactional(propagation = Propagation.REQUIRES_NEW)
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

	// Use it to extract our UserDetails based on the username that we read from the
	// token that we are going to receive
	// like authenticationFilter we need to extend some functionality to make
	// authorization work for us
	// dataset to have access to the authorization data

	@Autowired
	private UserRepository userRepository;

	public UserService userService = new UserServiceImpl(userRepository);

	public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
		super(authenticationManager);
		this.userRepository = userRepository;
	}

	public JwtAuthorizationFilter(AuthenticationManager authenticationManager,
			AuthenticationEntryPoint authenticationEntryPoint, UserRepository userRepository) {
		super(authenticationManager, authenticationEntryPoint);
		this.userRepository = userRepository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// Read the Authorization header, where the JWT token should be
		String header = request.getHeader(com.thp.spring.simplecontext.security.JwtProperties.HEADER_STRING);

		// If header does not contain BEARER or is null delegate to Spring impl and exit
		if (header == null || !header.startsWith(com.thp.spring.simplecontext.security.JwtProperties.TOKEN_PREFIX)) {
			chain.doFilter(request, response);
			return;
		}

		// If header is present, try grab user principal from database and perform
		// authorization

		String token = request.getHeader(JwtProperties.HEADER_STRING);

		if (token != null) {
			// parse the token.
			String mail = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET.getBytes())).build()
					.verify(token.replace(JwtProperties.TOKEN_PREFIX, "")).getSubject();

			if (mail != null) {
				// new arraylist means authorities

				User user = userRepository.findByMail(mail);
				UserPrincipal principal = new UserPrincipal(user);

				Collection<? extends GrantedAuthority> authorities = principal.getAuthorities();

				Authentication authentication = new UsernamePasswordAuthenticationToken(principal, null, authorities);

				SecurityContextHolder.getContext().setAuthentication(authentication);

				// Continue filter execution
				chain.doFilter(request, response);
			}
		}

	}
}
