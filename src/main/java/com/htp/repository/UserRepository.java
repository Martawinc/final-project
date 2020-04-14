package com.htp.repository;

import com.htp.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

  User findByUsername(String login);

  Optional<User> findByIdAndDeletedFalse(Long id);

  List<User> findByDeletedFalse();

  @Modifying(flushAutomatically = true)
  @Query("update User user set user.role = :roleId where user.id = :id")
  void updateRole(long roleId, long id);
}
