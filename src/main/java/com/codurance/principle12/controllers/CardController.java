package com.codurance.principle12.controllers;

import com.codurance.principle12.exceptions.CardNotFoundException;
import com.codurance.principle12.exceptions.ColumnNotFoundException;
import com.codurance.principle12.entities.Card;
import com.codurance.principle12.exceptions.UnauthorizedException;
import com.codurance.principle12.requests.NewCardRequestObject;
import com.codurance.principle12.requests.UpVoteRequestObject;
import com.codurance.principle12.requests.UpdateCardRequestObject;
import com.codurance.principle12.responses.CardResponseObject;
import com.codurance.principle12.services.ApplicationCardService;
import com.codurance.principle12.services.CardService;
import com.codurance.principle12.services.LoginService;
import java.util.Collections;
import java.util.List;
import javax.validation.Valid;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cards")
public class CardController extends BaseController {

  private final LoginService loginService;
  private CardService cardService;
  private ApplicationCardService applicationCardService;

  @Autowired
  public CardController(CardService cardService, ApplicationCardService applicationCardService,
      LoginService loginService) {
    this.cardService = cardService;
    this.applicationCardService = applicationCardService;
    this.loginService = loginService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public CardResponseObject postCard(@RequestBody @Valid NewCardRequestObject request,
      @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
    if (!loginService.isAuthorized(request.getEmail(), token)) {
      throw new UnauthorizedException();
    }

    return applicationCardService.create(request);
  }

  @DeleteMapping(value = "/{cardId}")
  public ResponseEntity<HttpStatus> deleteCard(@PathVariable Long cardId) {
    cardService.delete(cardId);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PatchMapping(value = "/{cardId}")
  public Card updateText(@PathVariable Long cardId, @RequestBody @Valid UpdateCardRequestObject request) {
    return cardService.updateText(cardId, request);
  }

  @PatchMapping(value = "/{cardId}/vote")
  public CardResponseObject updateVote(@PathVariable Long cardId, @RequestBody @Valid UpVoteRequestObject request) {
    return request.getAddVote() ?
        applicationCardService.addUpvote(cardId, request) :
        applicationCardService.removeUpvote(cardId, request);
  }

  @ExceptionHandler(ColumnNotFoundException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public List<String> handleColumnNotFound(ColumnNotFoundException exception) {
    return Collections.singletonList(exception.getMessage());
  }

  @ExceptionHandler(CardNotFoundException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public List<String> handleCardNotFound(CardNotFoundException exception) {
    return Collections.singletonList(exception.getMessage());
  }

}