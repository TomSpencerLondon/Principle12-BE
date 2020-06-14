package com.codurance.principle12.repositories;

import com.codurance.principle12.entities.Card;

public interface CardRepository {

  Card addCard(Card newCard);

  void delete(Long cardId);

  Card updateText(Long cardId, String newText);

  Card addUpvote(Long cardId, Long userId);

  Card removeUpvote(Long cardId, Long userId);
}
