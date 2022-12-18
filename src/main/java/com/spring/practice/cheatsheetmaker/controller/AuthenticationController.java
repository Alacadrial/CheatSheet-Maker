package com.spring.practice.cheatsheetmaker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.practice.cheatsheetmaker.dto.AuthenticationRequest;
import com.spring.practice.cheatsheetmaker.dto.AuthenticationResponse;
import com.spring.practice.cheatsheetmaker.service.MyUserDetailsService;
import com.spring.practice.cheatsheetmaker.util.JwtUtil;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthenticationController {

  @Autowired
  private JwtUtil jwtUtil;
  @Autowired
  private MyUserDetailsService userDetailsService;
  @Autowired
  private AuthenticationManager authenticationManager;

  @PostMapping()
  public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest request) throws Exception {

    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

    } catch (BadCredentialsException e) {
      throw new Exception("Incorrect username or password.");
    }

    UserDetails user = userDetailsService.loadUserByUsername(request.getUsername());
    final String jwt = jwtUtil.generateToken(user);

    log.info("Jwt Generated for user: " + request.getUsername() + "\nJwt: Bearer " + jwt);
    return ResponseEntity.ok(new AuthenticationResponse(jwt));
  }



}
