package com.codurance.principle12.exceptions;

public class ColumnNotFoundException extends RuntimeException {

  public ColumnNotFoundException(String message) {
    super(message);
  }
}
