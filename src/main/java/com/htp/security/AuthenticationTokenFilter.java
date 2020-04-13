package com.htp.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class AuthenticationTokenFilter extends UsernamePasswordAuthenticationFilter {

  private final TokenUtils tokenUtils;
  private final UserDetailsService userDetailsService;

  public AuthenticationTokenFilter(
      TokenUtils tokenUtils,
      UserDetailsService userDetailsService,
      AuthenticationManager authenticationManager) {
    this.tokenUtils = tokenUtils;
    this.userDetailsService = userDetailsService;
    super.setAuthenticationManager(authenticationManager);
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    HttpServletRequest httpRequest = (HttpServletRequest) request;
    String token = httpRequest.getHeader(Headers.AUTH_TOKEN);

    if (token != null) {
      String username = tokenUtils.getUsernameFromToken(token);

      if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        UserDetails user = userDetailsService.loadUserByUsername(username);
        if (tokenUtils.validateToken(token, user)) {
          UsernamePasswordAuthenticationToken authenticationToken =
              new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
          authenticationToken.setDetails(
              new WebAuthenticationDetailsSource().buildDetails(httpRequest));
          SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
      }
    }

    chain.doFilter(request, response);
  }
}
