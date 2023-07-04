package com.learing.springPizzeria.exeptions;

public class NotUniqueNameExeption extends RuntimeException{
    public NotUniqueNameExeption(String message) {
      super(message);
    }
}
