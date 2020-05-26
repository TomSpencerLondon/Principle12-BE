package com.codurance.principle12.controllers;

import com.codurance.principle12.exceptions.CategoryNotFoundException;
import com.codurance.principle12.models.Card;
import com.codurance.principle12.requests.NewCardRequestObject;
import com.codurance.principle12.services.CardService;
import java.util.Collections;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cards")
public class CardController extends BaseController {

  private final CardService cardService;

  @Autowired
  public CardController(CardService cardService) {
    this.cardService = cardService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Card postCard(@RequestBody @Valid NewCardRequestObject request) {
    return cardService.addCard(request);
  }

  @ExceptionHandler(CategoryNotFoundException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public List<String> handleCategoryNotFound(CategoryNotFoundException exception) {
    return Collections.singletonList(exception.getMessage());
  }

}