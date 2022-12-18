package com.spring.practice.cheatsheetmaker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.practice.cheatsheetmaker.model.Authority;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Integer> {
  public List<Authority> findByRole(String role);
}
