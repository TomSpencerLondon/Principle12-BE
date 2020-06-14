package com.codurance.principle12.services;

import com.codurance.principle12.entities.Board;
import com.codurance.principle12.entities.User;
import com.codurance.principle12.exceptions.BoardNotFoundException;
import com.codurance.principle12.responses.BoardResponseObject;
import com.codurance.principle12.responses.BoardResponseObjectFactory;
import org.springframework.stereotype.Service;

@Service
public class ApplicationBoardService {
  private UserService userService;
  private BoardResponseObjectFactory boardResponseObjectFactory;
  private BoardService boardService;

  public ApplicationBoardService(UserService userService,
      BoardResponseObjectFactory boardResponseObjectFactory, BoardService boardService) {
    this.userService = userService;
    this.boardResponseObjectFactory = boardResponseObjectFactory;
    this.boardService = boardService;
  }

  public BoardResponseObject getBoard(User user, Long boardId) {
    try {
      userService.registerUserIfNotExists(user, boardId);
      user = userService.findByEmail(user.email);
      Board board = boardService.getBoard(boardId);
      return getResponseFrom(board, user.getId());
    } catch (RuntimeException exc) {
      throw new BoardNotFoundException();
    }
  }

  private BoardResponseObject getResponseFrom(Board board, Long userId) {
    return boardResponseObjectFactory.create(board, userId);
  }
}
