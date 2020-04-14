package com.htp.controller.converter;

import com.htp.controller.requests.UserCreateRequest;
import com.htp.domain.User;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public abstract class UserRequestConverter<S, T> extends EntityConverter<S, T> {

  protected User convertToUser(User user, UserCreateRequest request) {

    user.setFullName(request.getFullName());
    user.setBirthDate(request.getBirthDate());
    user.setCity(request.getCity());
    user.setStreet(request.getStreet());
    user.setZip(request.getZip());
    user.setPhoneNumber(request.getPhoneNumber());
    user.setMail(request.getMail());
    user.setDeleted(false);

    return user;
  }
}
