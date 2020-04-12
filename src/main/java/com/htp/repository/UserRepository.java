package com.htp.repository;

import com.htp.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

  User findByUsername(String login);
}
