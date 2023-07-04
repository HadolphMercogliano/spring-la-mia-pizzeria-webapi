package com.learing.springPizzeria.exeptions;

public class PizzaNotFound extends RuntimeException {
  public PizzaNotFound(String message) {
    super(message);
  }
}
