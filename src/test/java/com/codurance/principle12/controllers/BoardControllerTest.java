package com.codurance.principle12.controllers;

import com.codurance.principle12.services.BoardService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(BoardController.class)
public class BoardControllerTest {

  private static final String URL = "/board";

  @MockBean
  private BoardService boardService;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

//  @Test
//  void returns_a_board() throws Exception {
//    when(boardService.getBoard()).thenReturn(new Board(Collections.emptyList()));
//
//    Board board = requestBoard();
//
//    assertNotNull(board);
//  }
//
//  @Test
//  void returns_board_with_columns() throws Exception {
//    int columnID = 1;
//    List<Column> columns = List.of(new Column(columnID, "start", Collections.emptyList()));
//    when(boardService.getBoard()).thenReturn(new Board(columns));
//
//    Board board = requestBoard();
//
//    assertEquals(columns.size(), board.getColumns().size());
//    Column columnResponse = board.getColumns().get(0);
//    assertEquals(columnID, columnResponse.getId());
//  }
//
//  @Test
//  void returns_board_with_columns_and_cards() throws Exception {
//    int columnID = 1;
//    String text = "hello";
//    int cardId = 1;
//    String userName = "John Doe";
//    List<Card> cards = List.of(new Card(cardId, text, columnID, userName));
//    List<Column> columns = List.of(new Column(columnID, "start", cards));
//    when(boardService.getBoard()).thenReturn(new Board(columns));
//
//    Board board = requestBoard();
//
//    Column columnResponse = board.getColumns().get(0);
//    assertEquals(cards.size(), columnResponse.getCards().size());
//    Card cardResponse = columnResponse.getCards().get(0);
//    assertEquals(text, cardResponse.getText());
//    assertEquals(cardId, cardResponse.getId());
//    assertEquals(columnID, cardResponse.getColumnId());
//    assertEquals(userName, cardResponse.getUserName());
//  }
//
//  private Board requestBoard() throws Exception {
//    MvcResult httpResponse = mockMvc.perform(MockMvcRequestBuilders.get(URL))
//        .andExpect(status().isOk()).andReturn();
//    String contentAsString = httpResponse.getResponse().getContentAsString();
//    return objectMapper.readValue(contentAsString, new TypeReference<>() {
//    });
//  }
}
