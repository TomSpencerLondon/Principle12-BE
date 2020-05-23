package com.codurance.principle12.factories;

import com.codurance.principle12.models.Card;
import com.codurance.principle12.requests.NewCardRequestObject;
import org.springframework.stereotype.Component;

@Component
public class CardFactory {

  public Card create(NewCardRequestObject requestObject) {
    int id = CardIDGenerator.nextID();
    return new Card(id, requestObject.getText(), requestObject.getColumnId(), requestObject.getUserName());
  }
}