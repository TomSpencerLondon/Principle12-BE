package com.codurance.principle12.exceptions;

public class CategoryNotFoundException extends RuntimeException {

  public CategoryNotFoundException(String message) {
    super(message);
  }
}
