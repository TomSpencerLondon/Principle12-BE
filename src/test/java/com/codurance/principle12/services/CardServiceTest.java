package com.codurance.principle12.services;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.codurance.principle12.entities.User;
import com.codurance.principle12.exceptions.CardNotFoundException;
import com.codurance.principle12.exceptions.ColumnNotFoundException;
import com.codurance.principle12.exceptions.UserAlreadyUpvotedException;
import com.codurance.principle12.factories.CardFactory;
import com.codurance.principle12.entities.Card;
import com.codurance.principle12.repositories.CardRepository;
import com.codurance.principle12.requests.NewCardRequestObject;
import com.codurance.principle12.requests.UpdateCardRequestObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CardServiceTest {

  private final Boolean HAVE_VOTED = false;
  private final Long NON_EXISTENT_CARD_ID = 999L;
  private final Long CARD_ID = 1L;
  private final Long COLUMN_ID = 2L;
  private final User USER = new User(3L, "john.doe@codurance.com", "John Doe");
  private final String TEXT = "Hello";
  private final String NEW_TEXT = "updated hello";

  @Mock
  private CardFactory cardFactory;

  @Mock
  private CardRepository cardRepository;

  @Mock
  private UserService userService;

  private CardService cardService;

  @BeforeEach
  void setUp() {
    cardService = new CardService(cardFactory, cardRepository);
  }

  @Test
  public void should_create_and_return_new_card() {
    Card card = new Card(CARD_ID, TEXT, COLUMN_ID, USER.getId(), emptyList());
    User author = new User(USER.getId(), USER.email, USER.username);
    NewCardRequestObject requestObject = new NewCardRequestObject(TEXT, COLUMN_ID, USER.email);
    when(cardFactory.create(requestObject)).thenReturn(card);
    when(cardRepository.addCard(card)).thenReturn(card);
    cardService.create(requestObject);

    verify(cardFactory).create(requestObject);
    verify(cardRepository).addCard(card);
  }

  @Test
  public void should_delete_card_from_the_repository() {
    cardService.delete(CARD_ID);
    verify(cardRepository).delete(CARD_ID);
  }

  @Test
  public void should_throw_CardNotFoundException_when_cardId_is_invalid_on_delete() {
    doThrow(new RuntimeException()).when(cardRepository).delete(NON_EXISTENT_CARD_ID);
    assertThrows(CardNotFoundException.class, () -> cardService.delete(NON_EXISTENT_CARD_ID));
  }

  @Test
  void should_change_card_text_and_return_edited_card() {
    UpdateCardRequestObject requestObject = new UpdateCardRequestObject(NEW_TEXT);
    Card editedCard = new Card(CARD_ID, NEW_TEXT, COLUMN_ID, USER.getId(), emptyList());
    when(cardRepository.updateText(editedCard.getId(), requestObject.getNewText())).thenReturn(editedCard);

    Card card = cardService.updateText(editedCard.getId(), requestObject);

    assertEquals(NEW_TEXT, card.getText());
  }

  @Test
  void should_add_card_voter_and_return_card() {
    Card editedCard = new Card(CARD_ID, TEXT, COLUMN_ID, USER.getId(), singletonList(USER.getId()));
    when(cardRepository.addUpvote(editedCard.getId(), editedCard.getUserId())).thenReturn(editedCard);

    Card card = cardService.addUpvote(CARD_ID, USER.getId());

    assertEquals(editedCard.getVoters().size(), card.getVoters().size());
  }

  @Test
  public void should_throw_UserAlreadyUpvotedException_when_user_has_already_upvoted() {
    doThrow(new UserAlreadyUpvotedException()).when(cardRepository).addUpvote(CARD_ID, USER.getId());
    assertThrows(UserAlreadyUpvotedException.class, () -> {
      cardService.addUpvote(CARD_ID, USER.getId());
    });
  }

  @Test
  public void should_throw_CardNotFoundException_on_upvote() {
    doThrow(new RuntimeException()).when(cardRepository).addUpvote(NON_EXISTENT_CARD_ID, USER.getId());
    assertThrows(CardNotFoundException.class, () -> {
      cardService.addUpvote(NON_EXISTENT_CARD_ID, USER.getId());
    });
  }

  @Test
  void should_remove_card_voter_and_return_card() {
    Card editedCard = new Card(CARD_ID, TEXT, COLUMN_ID, USER.getId(), emptyList());

    when(cardRepository.removeUpvote(editedCard.getId(), editedCard.getUserId())).thenReturn(editedCard);

    Card card = cardService.removeUpvote(CARD_ID, USER.getId());

    assertEquals(editedCard.getVoters().size(), card.getVoters().size());
  }

  @Test
  public void should_throw_CardNotFoundException_on_updateText() {
    doThrow(new RuntimeException()).when(cardRepository).updateText(NON_EXISTENT_CARD_ID, NEW_TEXT);
    assertThrows(CardNotFoundException.class,
        () -> cardService.updateText(NON_EXISTENT_CARD_ID, new UpdateCardRequestObject(NEW_TEXT)));
  }

  @Test
  public void should_throw_ColumnNotFoundException_on_create() {
    when(userService.findByEmail(USER.email)).thenReturn(new User(USER.getId(), USER.email, USER.username));
    doThrow(new RuntimeException()).when(cardRepository).addCard(new Card());
    assertThrows(ColumnNotFoundException.class, () -> cardService.create(new NewCardRequestObject(TEXT, COLUMN_ID, USER.email)));
  }

}