package com.htp.controller.converter;

import com.htp.controller.requests.UserCreateRequest;
import com.htp.domain.User;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class UserCreateRequestConverter extends UserRequestConverter<UserCreateRequest, User>{

    private  PasswordEncoder encoder;

    @Autowired
    private void setPasswordEncoder(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    @Override
    public User convert(UserCreateRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(encoder.encode(request.getPassword()));
        return convertToUser(user, request);
    }
}
