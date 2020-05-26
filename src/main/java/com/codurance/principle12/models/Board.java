package com.codurance.principle12.models;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@RequiredArgsConstructor
@NoArgsConstructor
public class Board {

    @GeneratedValue
    @Id
    private Long id;

    @NonNull
    @OneToMany(targetEntity = Category.class)
    private List<Category> categories;

}
