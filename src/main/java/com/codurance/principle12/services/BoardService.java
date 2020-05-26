package com.codurance.principle12.services;

import com.codurance.principle12.models.Board;
import com.codurance.principle12.models.Card;
import com.codurance.principle12.repositories.BoardRepository;
import com.codurance.principle12.repositories.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardService {

  private final BoardRepository boardRepository;
  private final CardRepository cardRepository;

  @Autowired
  public BoardService(BoardRepository boardRepository, CardRepository cardRepository) {
    this.boardRepository = boardRepository;
    this.cardRepository = cardRepository;

  }

  public Board getBoard() {
    return boardRepository.findAll().iterator().next();
  }

  public Card addCard(Card card) {
    cardRepository.save(card);
    return card;
  }
}
