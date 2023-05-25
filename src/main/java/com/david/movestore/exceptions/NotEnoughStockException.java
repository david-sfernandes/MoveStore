package com.david.movestore.exceptions;

public class NotEnoughStockException extends Exception {
  public NotEnoughStockException(Integer productId, Integer qty) {
    super(NotEnoughStockException.generateMessage(productId, qty));
  }

  private static String generateMessage(Integer productId, Integer qty) {
    return "Not enough items avaliable for id = " + productId + ". Only " + qty + " avaliable.";
  }
}
