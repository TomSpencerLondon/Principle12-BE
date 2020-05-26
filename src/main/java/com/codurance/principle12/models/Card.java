package com.codurance.principle12.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Card {
  @Id
  private Long id;
  private String text;
  private Long categoryId;
  private String userName;

}
