package com.spring.practice.cheatsheetmaker.exception;

import javax.naming.AuthenticationException;

public class UsernameAlreadyExistsException extends AuthenticationException {
  public UsernameAlreadyExistsException(final String msg) {
    super(msg);
  }  
}
