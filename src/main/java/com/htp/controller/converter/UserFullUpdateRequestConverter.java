package com.htp.controller.converter;

import com.htp.controller.requests.UserUpdateRequest;
import com.htp.domain.User;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;

import static java.util.Optional.ofNullable;

@Component
public class UserFullUpdateRequestConverter extends UserRequestConverter<UserUpdateRequest, User> {

    @Override
  public User convert(UserUpdateRequest request) {
    User user    =
        ofNullable(entityManager.find(User.class, request.getId()))
            .orElseThrow(EntityNotFoundException::new);
    return convertToUser(user, request);
  }
}
