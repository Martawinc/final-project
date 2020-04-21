package com.htp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class AuthenticationTokenFilter extends UsernamePasswordAuthenticationFilter {

	private final TokenUtils tokenUtils;
	private final UserDetailsService userDetailsService;

	public AuthenticationTokenFilter(TokenUtils tokenUtils, UserDetailsService userDetailsService) {
		this.tokenUtils = tokenUtils;
		this.userDetailsService = userDetailsService;
	}

	@Autowired
	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		super.setAuthenticationManager(authenticationManager);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		Authentication authentication = getAuthentication((HttpServletRequest) request);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		chain.doFilter(request, response);
	}

	private Authentication getAuthentication(HttpServletRequest request) {
		String token = request.getHeader(Headers.AUTH_TOKEN);
		if (token != null && tokenUtils.validateToken(token)) {
			String username = tokenUtils.getUsernameFromToken(token);

			if (username != null) {
				UserDetails user = userDetailsService.loadUserByUsername(username);
				return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
			}
		}

		return null;
	}
}
