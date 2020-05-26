package com.codurance.principle12.repositories;

import com.codurance.principle12.models.Card;
import org.springframework.data.repository.CrudRepository;


public interface CardRepository extends CrudRepository<Card, Long> {

}
