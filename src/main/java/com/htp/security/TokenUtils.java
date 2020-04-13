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
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TokenUtils {
  private static final String ROLES = "roles";
  private final JwtTokenConfig tokenConfig;

  private String generateToken(Claims claims) {
    return Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(generateCurrentDate())
        .setExpiration(generateExpirationDate())
        .signWith(SignatureAlgorithm.HS512, tokenConfig.getSecret())
        .compact();
  }

  public String generateToken(UserDetails user) {
    Claims claims = Jwts.claims();
    claims.setSubject(user.getUsername());
    claims.put(ROLES, getEncryptedRoles(user));
    return generateToken(claims);
  }

  public Boolean validateToken(String token) {
      return !getExpirationDateFromToken(token).before(generateCurrentDate());
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
    return user.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .map(x -> x.replace("ROLE_", ""))
        .map(String::toLowerCase)
        .collect(Collectors.toList());
  }

  public String getUsernameFromToken(String token) {
    return getClaimsFromToken(token).getSubject();
  }

  public Date getIssuedDateFromToken(String token) {
    return getClaimsFromToken(token).getIssuedAt();
  }

  public Date getExpirationDateFromToken(String token) {
    return getClaimsFromToken(token).getExpiration();
  }

  private Claims getClaimsFromToken(String token) {
    return Jwts.parser().setSigningKey(tokenConfig.getSecret()).parseClaimsJws(token).getBody();
  }
}
