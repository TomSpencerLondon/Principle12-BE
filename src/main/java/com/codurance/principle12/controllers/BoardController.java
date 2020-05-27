package com.codurance.principle12.controllers;


import com.codurance.principle12.models.Board;
import com.codurance.principle12.services.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/board")
public class BoardController extends BaseController {

  private final BoardService boardService;

  @Autowired
  public BoardController(BoardService boardService) {
    this.boardService = boardService;
  }

  @GetMapping
  public Board getBoard() {
    return boardService.getBoard();
  }

  @GetMapping("/hello")
  public String getHello(){
    return System.getenv("HELLO");
  }

}