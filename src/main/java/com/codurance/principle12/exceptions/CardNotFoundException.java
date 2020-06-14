package com.codurance.principle12.exceptions;

public class CardNotFoundException extends RuntimeException{
  public CardNotFoundException() {
    super("Card Id is not valid");
  }
}
