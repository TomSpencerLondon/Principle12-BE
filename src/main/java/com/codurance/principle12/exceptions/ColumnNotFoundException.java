package com.codurance.principle12.exceptions;

public class ColumnNotFoundException extends RuntimeException {

  public ColumnNotFoundException() {
    super("Column Id is not valid");
  }
}
