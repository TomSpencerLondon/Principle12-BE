package com.codurance.principle12.services;

import com.codurance.principle12.entities.Board;
import com.codurance.principle12.entities.Card;
import com.codurance.principle12.entities.User;
import com.codurance.principle12.exceptions.BoardNotFoundException;
import com.codurance.principle12.factories.BoardFactory;
import com.codurance.principle12.repositories.BoardRepository;
import com.codurance.principle12.requests.NewBoardRequestObject;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardService {

  private final BoardRepository boardRepository;
  private final BoardFactory boardFactory;
  private final UserService userService;

  @Autowired
  public BoardService(BoardRepository boardRepository, BoardFactory boardFactory,
      UserService userService) {
    this.boardRepository = boardRepository;
    this.boardFactory = boardFactory;
    this.userService = userService;
  }

  public Board getBoard(Long boardId) {
    try {
      return boardRepository.getBoard(boardId);
    } catch (RuntimeException exc) {
      throw new BoardNotFoundException();
    }
  }

  public Board createBoard(NewBoardRequestObject requestObject) {
    Board savedBoard = boardRepository.insert(boardFactory.create(requestObject));
    userService.registerUserIfNotExists(requestObject.getUser(), savedBoard.getId());
    return savedBoard;
  }

  public List<Board> getUsersBoards(User requestUser) {
    User user = userService.findOrCreateBy(requestUser);
    return boardRepository.getUsersBoards(user.getId());
  }
}
