package com.htp.repository;

import com.htp.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsername(String login);

	Optional<User> findByIdAndDeletedFalse(Long id);

	List<User> findByDeletedFalse();
}
