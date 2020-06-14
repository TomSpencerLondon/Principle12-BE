package com.codurance.principle12.config.web;

import com.codurance.principle12.config.Environment;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile(Environment.TEST)
public class TestableTokenAuthenticator implements TokenAuthenticator {

  @Override
  public String getEmail(String token) {
    return "john.doe@codurance.com";
  }

  @Override
  public String getUsername(String token) {
    return "John Doe";
  }
}