package com.codurance.principle12.repositories;

import com.codurance.principle12.entities.User;

public interface UserRepository {

  User findByEmail(String email);

  User register(User user);

  void addToBoard(Long userId, Long boardId);

  User findById(Long id);
}
