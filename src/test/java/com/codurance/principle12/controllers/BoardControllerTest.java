package com.codurance.principle12.controllers;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.codurance.principle12.models.Board;
import com.codurance.principle12.models.Card;
import com.codurance.principle12.models.Category;
import com.codurance.principle12.services.BoardService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(BoardController.class)
public class BoardControllerTest {

  private static final String URL = "/board";

  @MockBean
  private BoardService boardService;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void returns_a_board() throws Exception {
    when(boardService.getBoard()).thenReturn(new Board(Collections.emptyList()));

    Board board = requestBoard();

    assertNotNull(board);
  }

  @Test
  void returns_board_with_columns() throws Exception {
    Long columnID = 1L;
    List<Category> categories = List.of(new Category(columnID, "start", Collections.emptyList()));
    when(boardService.getBoard()).thenReturn(new Board(categories));

    Board board = requestBoard();

    assertEquals(categories.size(), board.getCategories().size());
    Category categoryResponse = board.getCategories().get(0);
    assertEquals(columnID, categoryResponse.getId());
  }

  @Test
  void returns_board_with_columns_and_cards() throws Exception {
    Long columnID = 1L;
    String text = "hello";
    Long cardId = 1L;
    String userName = "John Doe";
    List<Card> cards = List.of(new Card(cardId, text, columnID, userName));
    List<Category> categories = List.of(new Category(columnID, "start", cards));
    when(boardService.getBoard()).thenReturn(new Board(categories));

    Board board = requestBoard();

    Category categoryResponse = board.getCategories().get(0);
    assertEquals(cards.size(), categoryResponse.getCards().size());
    Card cardResponse = categoryResponse.getCards().get(0);
    assertEquals(text, cardResponse.getText());
    assertEquals(cardId, cardResponse.getId());
    assertEquals(columnID, cardResponse.getCategoryId());
    assertEquals(userName, cardResponse.getUserName());
  }

  private Board requestBoard() throws Exception {
    MvcResult httpResponse = mockMvc.perform(MockMvcRequestBuilders.get(URL))
        .andExpect(status().isOk()).andReturn();
    String contentAsString = httpResponse.getResponse().getContentAsString();
    return objectMapper.readValue(contentAsString, new TypeReference<>() {
    });
  }
}
