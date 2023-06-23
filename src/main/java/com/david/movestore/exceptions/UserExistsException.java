package com.david.movestore.exceptions;

import com.david.movestore.SimpleResponse.SimpleResponse;

public class UserExistsException extends Exception {
  public UserExistsException(String email) {
    super(UserExistsException.generateMessage(email));
  }

  private static String generateMessage(String email) {
    return SimpleResponse.builder().message("A user with e-mail " + email + " already exists.").build().toString();
  }
}
