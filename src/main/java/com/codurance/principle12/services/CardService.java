package com.codurance.principle12.services;

import com.codurance.principle12.factories.CardFactory;
import com.codurance.principle12.models.Card;
import com.codurance.principle12.requests.NewCardRequestObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardService {

  private final CardFactory cardFactory;
  private final BoardService boardService;

  @Autowired
  public CardService(CardFactory cardFactory, BoardService boardService) {
    this.boardService = boardService;
    this.cardFactory = cardFactory;
  }

  public Card addCard(NewCardRequestObject requestObject) {
    Card card = cardFactory.create(requestObject);
    return boardService.addCard(card);
  }
}
