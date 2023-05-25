package com.david.movestore.exceptions;

public class NotNullFileException extends Exception {
  public NotNullFileException(Class c, String param) {
    super(NotNullFileException.generateMessage(c.getSimpleName(), param));
  }

  private static String generateMessage(String entity, String param) {
    return entity + " file for "+ param + " is required." ;
  }
}
