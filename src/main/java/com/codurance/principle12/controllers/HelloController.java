package com.codurance.principle12.controllers;

import com.codurance.principle12.models.Board;
import com.codurance.principle12.services.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController extends BaseController {

  @GetMapping
  public String hello() {
    return System.getenv("HELLO") + " THIS WORKS";
  }
}
