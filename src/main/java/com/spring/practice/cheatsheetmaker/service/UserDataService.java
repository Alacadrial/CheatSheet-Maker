package com.spring.practice.cheatsheetmaker.service;

import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.practice.cheatsheetmaker.dto.RegisterationRequest;
import com.spring.practice.cheatsheetmaker.exception.UsernameAlreadyExistsException;
import com.spring.practice.cheatsheetmaker.model.Authority;
import com.spring.practice.cheatsheetmaker.model.User;
import com.spring.practice.cheatsheetmaker.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserDataService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private BCryptPasswordEncoder passwordEncoder;

  public void insertUser(RegisterationRequest request) throws UsernameAlreadyExistsException {
    if (userRepository.existsByUsername(request.getUsername())) {
      throw new UsernameAlreadyExistsException("User with username: " + request.getUsername() + " already exists.");
    }
    
    User user = new User();
    user.setUsername(request.getUsername());
    user.setPassword(passwordEncoder.encode(request.getPassword()));
    user.setEnabled(true);

    Authority authority = new Authority();
    authority.setRole("ROLE_USER");
    authority.setUsername(request.getUsername());

    user.setAuthorities(Collections.singletonList(authority));

    // if Authorities of the user is set before pushing the user to the DB, authorities are also sent with it in a single call.
    userRepository.save(user);
    log.info("New user created with username: " + request.getUsername());
  }

  public void test() {
    log.info("Test Call");
  }
}

