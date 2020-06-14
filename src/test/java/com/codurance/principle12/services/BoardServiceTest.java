package com.codurance.principle12.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.codurance.principle12.entities.Board;
import com.codurance.principle12.entities.Card;
import com.codurance.principle12.entities.Column;
import com.codurance.principle12.repositories.BoardRepository;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BoardServiceTest {


  @Mock
  private BoardRepository boardRepository;
  private BoardService boardService;

  @BeforeEach
  void setUp() {
    boardService = new BoardService(boardRepository);
  }

  @Test
  void should_return_a_board() {
    when(boardRepository.getBoard()).thenReturn(
        new Board(List.of(new Column(1, "Start", Collections.emptyList()))));

    Board board = boardService.getBoard();

    verify(boardRepository).getBoard();
    assertEquals(1, board.getColumns().get(0).getId());
    assertEquals("Start", board.getColumns().get(0).getTitle());
    assertEquals(0, board.getColumns().get(0).getCards().size());
  }

  @Test
  public void should_add_card_to_in_memory_and_return_new_card() {
    Card cardToBeAdded = new Card(1, "hello", 1, "John Doe");
    Card card = boardService.addCard(cardToBeAdded);

    verify(boardRepository).addCard(card);
    assertEquals(card.getText(), cardToBeAdded.getText());
    assertEquals(card.getId(), cardToBeAdded.getId());
    assertEquals(card.getColumnId(), cardToBeAdded.getColumnId());
    assertEquals(card.getUserName(), cardToBeAdded.getUserName());
  }

}
