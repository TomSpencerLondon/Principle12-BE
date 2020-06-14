package com.codurance.principle12.factories;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.codurance.principle12.entities.Card;
import com.codurance.principle12.requests.NewCardRequestObject;
import org.junit.jupiter.api.Test;

public class CardFactoryTest {

  @Test
  void returns_a_new_card() {
    String text = "hello";
    Long columnId = 1L;
    Long userId = 1L;
    String userEmail = "john.doe@codurance.com";
    NewCardRequestObject newCardRequestObject = new NewCardRequestObject(text, columnId, userEmail);
    newCardRequestObject.setUserId(userId);

    Card card = new CardFactory().create(newCardRequestObject);

    assertEquals(text, card.getText());
    assertEquals(columnId, card.getColumnId());
    assertEquals(userId, card.getUserId());
  }

}
