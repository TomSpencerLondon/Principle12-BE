package com.codurance.principle12.repositories;

import com.codurance.principle12.models.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {

}
