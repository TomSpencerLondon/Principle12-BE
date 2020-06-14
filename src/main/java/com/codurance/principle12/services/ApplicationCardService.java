package com.codurance.principle12.services;

import com.codurance.principle12.entities.Card;
import com.codurance.principle12.entities.User;
import com.codurance.principle12.requests.NewCardRequestObject;
import com.codurance.principle12.requests.UpVoteRequestObject;
import com.codurance.principle12.responses.CardResponseObject;
import com.codurance.principle12.responses.CardResponseObjectFactory;
import org.springframework.stereotype.Service;

@Service
public class ApplicationCardService {
  private final UserService userService;
  private final CardService cardService;
  private CardResponseObjectFactory cardResponseObjectFactory;

  public ApplicationCardService(UserService userService, CardService cardService,
      CardResponseObjectFactory cardResponseObjectFactory) {
    this.userService = userService;
    this.cardService = cardService;
    this.cardResponseObjectFactory = cardResponseObjectFactory;
  }

  public CardResponseObject create(NewCardRequestObject requestObject) {
    User user = userService.findByEmail(requestObject.getEmail());
    requestObject.setUserId(user.getId());

    Card card = cardService.create(requestObject);

    return createResponseFrom(card, user.getId());
  }

  public CardResponseObject addUpvote(Long cardId, UpVoteRequestObject requestObject) {
    User user = userService.findByEmail(requestObject.getEmail());
    Card updatedCard = cardService.addUpvote(cardId, user.getId());
    return createResponseFrom(updatedCard, user.getId());
  }

  public CardResponseObject removeUpvote(Long cardId, UpVoteRequestObject requestObject) {
    User user = userService.findByEmail(requestObject.getEmail());
    Card updatedCard = cardService.removeUpvote(cardId, user.getId());
    return createResponseFrom(updatedCard, user.getId());
  }

  private CardResponseObject createResponseFrom(Card card, Long userId) {
    User cardAuthor = userService.findById(userId);
    return cardResponseObjectFactory.create(card, userId, cardAuthor.username);
  }
}
