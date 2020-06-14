package com.codurance.principle12.services;

import com.codurance.principle12.entities.User;
import com.codurance.principle12.exceptions.UserNotFoundException;
import com.codurance.principle12.repositories.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class UserService {
  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public void registerUserIfNotExists(User requestUser, Long boardId) {
    User user = findOrCreateBy(requestUser);
    addToBoard(user.getId(), boardId);
  }

  public void addToBoard(Long userId, Long boardId) {
    userRepository.addToBoard(userId, boardId);
  }

  public User findOrCreateBy(User user) {
    try {
      return findByEmail(user.email);
    } catch (UserNotFoundException userNotFoundException) {
      return userRepository.register(user);
    }
  }

  public User findByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  public User findById(Long Id) {
    return userRepository.findById(Id);
  }
}
