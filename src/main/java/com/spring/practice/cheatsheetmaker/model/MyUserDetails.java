package com.spring.practice.cheatsheetmaker.model;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyUserDetails implements UserDetails {

  private String username;
  private String password;
  private Boolean isEnabled;
  private List<GrantedAuthority> authorities;

  private boolean isAccountNonExpired;
  private boolean isAccountNonLocked;
  private boolean isCredentialsNonExpired;

  public MyUserDetails(User user) {
    this.username = user.getUsername();
    this.password = user.getPassword();
    this.isEnabled = user.isEnabled();
    this.authorities = user.getAuthorities().stream().map(authority -> authority.getRole())
        .map(SimpleGrantedAuthority::new).collect(Collectors.toList());

    user.getAuthorities().forEach(authority -> log.info("Role Found: " + authority.getRole()));

    this.isAccountNonExpired = true;
    this.isAccountNonLocked = true;
    this.isCredentialsNonExpired = true;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.authorities;
  }

  @Override
  public String getPassword() {
    return this.password;
  }

  @Override
  public String getUsername() {
    return this.username;
  }

  // Check JWT iat?.
  @Override
  public boolean isAccountNonExpired() {
    return this.isAccountNonExpired;
  }

  // Check locked accounts table
  @Override
  public boolean isAccountNonLocked() {
    return this.isAccountNonLocked;
  }

  // Check security context maybe?
  @Override
  public boolean isCredentialsNonExpired() {
    return this.isCredentialsNonExpired;
  }

  @Override
  public boolean isEnabled() {
    return this.isEnabled;
  }

}
