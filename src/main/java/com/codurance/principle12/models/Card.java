package com.codurance.principle12.models;

public class Card {

  private int id;
  private int columnId;
  private String text;
  private String userName;

  public Card() {
  }

  public Card(int cardId, String cardText, int columnId, String userName) {
    id = cardId;
    text = cardText;
    this.columnId = columnId;
    this.userName = userName;
  }

  public String getText() {
    return text;
  }

  public int getColumnId() {
    return columnId;
  }

  public int getId() {
    return id;
  }

  public String getUserName() {
    return userName;
  }
}
