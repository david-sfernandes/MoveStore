package com.david.movestore.exceptions;

import jakarta.persistence.EntityNotFoundException;

@SuppressWarnings("rawtypes")
public class NotFoundException extends EntityNotFoundException {
  public NotFoundException(Class c, String param, String value) {
    super(NotFoundException.generateMessage(c.getSimpleName(), param, value));
  }

  private static String generateMessage(String entity, String param, String value) {
    return entity + " was not found for parameters {"+ param + ": " + value + "}" ;
  }
}
