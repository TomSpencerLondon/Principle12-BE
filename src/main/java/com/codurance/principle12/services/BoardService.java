package com.codurance.principle12.services;

import com.codurance.principle12.models.Board;
import com.codurance.principle12.models.Card;
import com.codurance.principle12.repositories.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardService {

  private final BoardRepository boardRepository;

  @Autowired
  public BoardService(BoardRepository boardRepository) {
    this.boardRepository = boardRepository;
  }

  public Board getBoard(Integer id) {
    return boardRepository.getBoard(id);
  }

  public Card addCard(Card card) {
    boardRepository.addCard(card);
    return card;
  }
}
