package com.codurance.principle12.repositories;

import com.codurance.principle12.exceptions.ColumnNotFoundException;
import com.codurance.principle12.models.Board;
import com.codurance.principle12.models.Card;
import com.codurance.principle12.models.Column;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryBoardRepository implements BoardRepository {

  private final Board board;

  public InMemoryBoardRepository() {
    this.board = new Board(List.of(
        new Column(0, "Start", new ArrayList<>()),
        new Column(1, "Stop", new ArrayList<>()),
        new Column(2, "Continue", new ArrayList<>())));

  }

  @Override
  public Board getBoard(Integer id) {
    return board;
  }

  @Override
  public void addCard(Card card) {
    Optional<Column> columnOptional = board.getColumns().stream()
        .filter(column -> card.getColumnId() == column.getId())
        .findAny();
    if (!columnOptional.isPresent()) {
      throw new ColumnNotFoundException("Column Id is not valid");
    }
    columnOptional.get().getCards().add(card);
  }

}
