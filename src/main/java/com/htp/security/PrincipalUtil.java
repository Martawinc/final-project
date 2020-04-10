package com.htp.security;

import com.htp.domain.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.security.Principal;

public class PrincipalUtil {

  public static Long getPrincipalId(Principal principal) {
    Object userPrincipal = ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
    return ((User) userPrincipal).getId();
  }
}
