package com.codurance.principle12.services;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.codurance.principle12.entities.Board;
import com.codurance.principle12.entities.Card;
import com.codurance.principle12.entities.Column;
import com.codurance.principle12.entities.User;
import com.codurance.principle12.factories.BoardFactory;
import com.codurance.principle12.repositories.BoardRepository;
import com.codurance.principle12.requests.NewBoardRequestObject;
import com.codurance.principle12.responses.BoardResponseObjectFactory;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BoardServiceTest {

  private final Long USER_ID = 2L;
  private final Long BOARD_ID = 1L;
  private final User USER = new User("john.doe@codurance.com", "John Doe");
  private final String BOARD_TITLE = "test board";

  @Mock
  private BoardRepository boardRepository;

  @Mock
  private BoardFactory boardFactory;

  @Mock
  private UserService userService;

  @Mock
  private BoardResponseObjectFactory boardResponseFactory;

  private BoardService boardService;

  @BeforeEach
  void setUp() {
    boardService = new BoardService(boardRepository, boardFactory, userService);
  }

  @Test
  void returns_a_board() {
    Board board = new Board(BOARD_ID, BOARD_TITLE, emptyList());
    when(boardRepository.getBoard(BOARD_ID)).thenReturn(board);

    assertEquals(BOARD_ID, boardService.getBoard(BOARD_ID).getId());
    assertEquals(BOARD_TITLE, boardService.getBoard(BOARD_ID).getTitle());
  }

  @Test
  void creates_a_board() {
    NewBoardRequestObject requestObject = new NewBoardRequestObject(BOARD_TITLE,
        "john.doe@codurance.com");
    requestObject.setUser(USER);
    Board board = new Board(BOARD_ID, BOARD_TITLE, emptyList());
    when(boardFactory.create(requestObject)).thenReturn(board);
    when(boardRepository.insert(board)).thenReturn(board);

    Board boardResponse = boardService.createBoard(requestObject);

    assertEquals(BOARD_ID, boardResponse.getId());
    assertEquals(BOARD_TITLE, boardResponse.getTitle());
    assertEquals(0, boardResponse.getColumns().size());
  }

  @Test
  void should_return_boards_for_a_user() {
    when(userService.findOrCreateBy(USER))
        .thenReturn(new User(USER_ID, USER.email, USER.username));
    when(boardRepository.getUsersBoards(USER_ID)).thenReturn(List.of(
        new Board(BOARD_ID, BOARD_TITLE, emptyList())));

    List<Board> boards = boardService.getUsersBoards(USER);

    assertEquals(1, boards.size());
    assertEquals(BOARD_ID, boards.get(0).getId());
    assertEquals(BOARD_TITLE, boards.get(0).getTitle());
  }
}
