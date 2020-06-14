package com.codurance.principle12.services;

import com.codurance.principle12.config.web.TokenAuthenticator;
import com.codurance.principle12.exceptions.UnauthorizedException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
  private TokenAuthenticator tokenAuthenticator;

  public LoginService(TokenAuthenticator tokenAuthenticator) {
    this.tokenAuthenticator = tokenAuthenticator;
  }

  public Boolean isAuthorized(String email, String token) {
    try {
      return tokenAuthenticator.getEmail(token).equals(email);
    } catch (GeneralSecurityException | IOException e) {
      throw new UnauthorizedException();
    }
  }
}
