package com.codurance.principle12.repositories;

import com.codurance.principle12.entities.Board;
import com.codurance.principle12.entities.Card;
import java.util.List;

public interface BoardRepository {

  Board getBoard(Long id);

  List<Board> getUsersBoards(Long userId);

  Board insert(Board board);
}
