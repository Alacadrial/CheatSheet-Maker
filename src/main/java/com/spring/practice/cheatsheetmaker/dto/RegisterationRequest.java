package com.spring.practice.cheatsheetmaker.dto;

import com.spring.practice.cheatsheetmaker.model.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterationRequest {
  private String username;
  private String password;

  public RegisterationRequest(User user) {
    this.username = user.getUsername();
    this.password = user.getPassword();
  }
}
