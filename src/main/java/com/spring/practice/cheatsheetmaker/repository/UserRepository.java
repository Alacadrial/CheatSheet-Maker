package com.spring.practice.cheatsheetmaker.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.practice.cheatsheetmaker.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
  public Optional<User> findByUsername(String username);
  public boolean existsByUsername(String username);
}
