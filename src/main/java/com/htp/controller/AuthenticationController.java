package com.htp.controller;

import com.htp.controller.requests.AuthRequest;
import com.htp.controller.responses.AuthResponse;
import com.htp.repository.UserRepository;
import com.htp.security.util.TokenUtils;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final TokenUtils tokenUtils;

    @ApiOperation(value = "User authentication process", notes = "method returns user's login and auth-token")
    @PostMapping
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authRequest.getUsername(),
                authRequest.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String token =  tokenUtils.generateToken(userRepository.findByUsername(authRequest.getUsername()));

        return ResponseEntity.ok(AuthResponse
                .builder()
                .login(authRequest.getUsername())
                .token(token)
                .build());
    }
}
