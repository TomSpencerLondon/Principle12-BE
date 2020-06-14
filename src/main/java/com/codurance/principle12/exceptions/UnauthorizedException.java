package com.codurance.principle12.exceptions;

public class UnauthorizedException extends RuntimeException {
  public UnauthorizedException() {
    super("Unauthorized");
  }
}
