package com.codurance.principle12.services;

import com.codurance.principle12.exceptions.CardNotFoundException;
import com.codurance.principle12.exceptions.ColumnNotFoundException;
import com.codurance.principle12.exceptions.UserAlreadyUpvotedException;
import com.codurance.principle12.factories.CardFactory;
import com.codurance.principle12.entities.Card;
import com.codurance.principle12.repositories.CardRepository;
import com.codurance.principle12.requests.NewCardRequestObject;
import com.codurance.principle12.requests.UpdateCardRequestObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardService {

  private final CardFactory cardFactory;
  private final CardRepository cardRepository;

  @Autowired
  public CardService(CardFactory cardFactory, CardRepository cardRepository) {
    this.cardFactory = cardFactory;
    this.cardRepository = cardRepository;
  }

  public Card create(NewCardRequestObject requestObject) {
    Card card;
    Card newCard = cardFactory.create(requestObject);

    try {
      card = cardRepository.addCard(newCard);
    } catch (RuntimeException exception) {
      throw new ColumnNotFoundException();
    }
    return card;
  }

  public void delete(Long cardId) {
    try {
      cardRepository.delete(cardId);
    } catch (RuntimeException exception) {
      throw new CardNotFoundException();
    }
  }

  public Card updateText(Long cardId, UpdateCardRequestObject requestObject) {
    try {
      return cardRepository.updateText(cardId, requestObject.getNewText());
    } catch (RuntimeException invalidCardId) {
      throw new CardNotFoundException();
    }
  }

  public Card addUpvote(Long cardId, Long userId) {
    try {
      Card updatedCard = cardRepository.addUpvote(cardId, userId);
      return updatedCard;
    } catch (UserAlreadyUpvotedException userUpvotedException) {
      throw userUpvotedException;
    } catch (RuntimeException invalidCardId) {
      throw new CardNotFoundException();
    }
  }

  public Card removeUpvote(Long cardId, Long userId) {
    try {
      Card updatedCard = cardRepository.removeUpvote(cardId, userId);
      return updatedCard;
    } catch (RuntimeException invalidCardId) {
      throw new CardNotFoundException();
    }
  }
}
