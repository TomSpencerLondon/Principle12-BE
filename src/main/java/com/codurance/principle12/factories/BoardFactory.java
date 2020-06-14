package com.codurance.principle12.factories;

import com.codurance.principle12.entities.Board;
import com.codurance.principle12.entities.Column;
import com.codurance.principle12.entities.ColumnType;
import com.codurance.principle12.requests.NewBoardRequestObject;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class BoardFactory {
  public Board create(NewBoardRequestObject requestObject) {
    return new Board(requestObject.getTitle(), List.of(
        new Column(ColumnType.START),
        new Column(ColumnType.STOP),
        new Column(ColumnType.CONTINUE)
    ));
  }
}
