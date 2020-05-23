package com.codurance.principle12.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.codurance.principle12.factories.CardFactory;
import com.codurance.principle12.models.Card;
import com.codurance.principle12.requests.NewCardRequestObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CardServiceTest {

  @Mock
  private CardFactory cardFactory;

  private CardService cardService;

  @Mock
  private BoardService boardService;

  @BeforeEach
  void setUp() {
    cardService = new CardService(cardFactory, boardService);
  }

  @Test
  public void should_add_and_return_new_card() {
    String text = "new card";
    int columnId = 1;
    int cardId = 1;
    String userName = "John Doe";
    NewCardRequestObject requestObject = new NewCardRequestObject(text, columnId, userName);
    Card card = new Card(cardId, text, columnId, userName);

    when(cardFactory.create(requestObject)).thenReturn(card);
    when(boardService.addCard(card)).thenReturn(card);

    assertEquals(card, cardService.addCard(requestObject));
  }

}
