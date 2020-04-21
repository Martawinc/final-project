package com.htp.controller.converter;

import com.htp.controller.requests.UserCreateRequest;
import com.htp.domain.User;
import com.htp.repository.RoleRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class UserCreateRequestConverter extends UserRequestConverter<UserCreateRequest, User> {

	private PasswordEncoder encoder;
	private RoleRepository roleRepo;

	@Autowired
	private void setPasswordEncoder(PasswordEncoder encoder) {
		this.encoder = encoder;
	}

	@Autowired
	private void setRoleRepo(RoleRepository roleRepo) {
		this.roleRepo = roleRepo;
	}

	@Override
	public User convert(UserCreateRequest request) {
		User user = new User();
		user.setUsername(request.getUsername());
		user.setPassword(encoder.encode(request.getPassword()));
		user.setRole(roleRepo.findById(2L).get());
		return convertToUser(user, request);
	}
}
