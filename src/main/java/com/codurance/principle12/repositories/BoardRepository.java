package com.codurance.principle12.repositories;

import com.codurance.principle12.models.Board;
import com.codurance.principle12.models.Card;

public interface BoardRepository {

  Board getBoard(Integer id);

  void addCard(Card card);
}
