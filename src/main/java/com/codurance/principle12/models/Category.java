package com.codurance.principle12.models;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Category {

  @Id
  private Long id;

  private String title;

  @OneToMany(targetEntity = Card.class)
  private List<Card> cards;
}
