package com.codurance.principle12.entities;

import com.fasterxml.jackson.annotation.JsonValue;
import java.util.HashMap;
import java.util.Map;

public enum ColumnType {
  START("Start"),
  STOP("Stop"),
  CONTINUE("Continue");

  @JsonValue
  private String title;

  private static final Map<String, ColumnType> map = new HashMap<>(values().length, 1);

  static {
    for (ColumnType c : values()) {
      map.put(c.title, c);
    }
  }

  ColumnType(String title) {
    this.title = title;
  }

  public String getTitle() {
    return title;
  }

  public static ColumnType of(String title) {
    ColumnType result = map.get(title);
    if (result == null) {
      throw new IllegalArgumentException("Invalid column title: " + title);
    }
    return result;
  }
}
