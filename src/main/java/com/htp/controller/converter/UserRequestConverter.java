package com.htp.controller.converter;

import com.htp.controller.requests.UserCreateRequest;
import com.htp.domain.User;
import com.htp.repository.UserRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@NoArgsConstructor
public abstract class UserRequestConverter<S, T> extends EntityConverter<S, T> {

  private UserRepository userRepo;

  @Autowired
  private void setUserRepo(UserRepository userRepo) {
    this.userRepo = userRepo;
  }

  protected User convertToUser(User user, UserCreateRequest request) {

    user.setFullName(request.getFullName());
    user.setBirthDate(request.getBirthDate());
    user.setCity(request.getCity());
    user.setStreet(request.getStreet());
    user.setZip(request.getZip());
    user.setPhoneNumber(request.getPhoneNumber());
    user.setMail(request.getMail());

    return user;
  }
}
