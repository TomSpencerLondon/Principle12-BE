package com.codurance.principle12.exceptions;

public class BoardNotFoundException extends RuntimeException {
  public BoardNotFoundException() {
    super("Board Id is not valid");
  }
}
