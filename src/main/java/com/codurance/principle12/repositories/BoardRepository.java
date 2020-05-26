package com.codurance.principle12.repositories;

import com.codurance.principle12.models.Board;
import com.codurance.principle12.models.Card;
import org.springframework.data.repository.CrudRepository;

public interface BoardRepository extends CrudRepository<Board, Long> {

//  Board getBoard();

//  void addCard(Card card);
}
