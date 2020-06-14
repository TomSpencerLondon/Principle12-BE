package com.codurance.principle12.factories;

import com.codurance.principle12.entities.Card;
import com.codurance.principle12.requests.NewCardRequestObject;
import java.util.Collections;
import org.springframework.stereotype.Component;

@Component
public class CardFactory {

  public Card create(NewCardRequestObject requestObject) {
    return new Card(requestObject.getText(), requestObject.getColumnId(), requestObject.getUserId(),
        Collections.emptyList());
  }
}