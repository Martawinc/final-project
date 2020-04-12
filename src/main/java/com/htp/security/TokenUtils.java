package com.htp.security;

import com.htp.config.JwtTokenConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TokenUtils {
  private static final String CREATED = "created";
  private static final String ROLES = "roles";
  private final JwtTokenConfig tokenConfig;

  private String generateToken(Map<String, Object> claims) {
    return Jwts
            .builder()
            .setClaims(claims)
            .setExpiration(generateExpirationDate())
            .signWith(SignatureAlgorithm.HS512, tokenConfig.getSecret())
            .compact();
  }

  public String generateToken(UserDetails user) {
    Map<String, Object> claims = new HashMap<>();
    claims.put(Claims.SUBJECT, user.getUsername());
    claims.put(CREATED, generateCurrentDate());
    claims.put(ROLES, getEncryptedRoles(user));
    return generateToken(claims);
  }

  public Boolean validateToken (String token, UserDetails user){
      String tokenUsername = getUsernameFromToken(token);
      return tokenUsername.equals(user.getUsername());
  }

  private Date generateExpirationDate() {
    Calendar expireDate = Calendar.getInstance();
    expireDate.setTimeInMillis(System.currentTimeMillis() + tokenConfig.getExpire());
    return expireDate.getTime();
  }

  private Date generateCurrentDate() {
    return new Date();
  }

  private List<String> getEncryptedRoles(UserDetails user) {
    return user.getAuthorities()
            .stream()
            .map(GrantedAuthority::getAuthority)
            .map(x -> x.replace("ROLE_", ""))
            .map(String::toLowerCase)
            .collect(Collectors.toList());
  }

  public String getUsernameFromToken(String token) {
    return getClaimsFromToken(token).getSubject();
  }

  public Date getCreatedDateFromToken(String token) {
    return (Date) getClaimsFromToken(token).get(CREATED);
  }

  public Date getExpirationDateFromToken(String token) {
    return getClaimsFromToken(token).getExpiration();
  }

  private Claims getClaimsFromToken(String token) {
    return Jwts
            .parser()
            .setSigningKey(tokenConfig.getSecret())
            .parseClaimsJws(token)
            .getBody();
  }
}
