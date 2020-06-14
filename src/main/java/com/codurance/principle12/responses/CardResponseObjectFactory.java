package com.codurance.principle12.responses;

import com.codurance.principle12.entities.Card;

public class CardResponseObjectFactory {
  public CardResponseObject create(Card newCard, Long userId, String author) {
    Integer numberOfVoters = newCard.getVoters().size();
    boolean hasVoted = newCard.getVoters().contains(userId);
    return new CardResponseObject(newCard.getText(), newCard.getId(), newCard.getColumnId(),
        hasVoted, numberOfVoters, author);
  }
}
