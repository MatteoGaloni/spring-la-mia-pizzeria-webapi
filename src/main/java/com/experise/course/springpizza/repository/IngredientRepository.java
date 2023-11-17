package com.experise.course.springpizza.repository;

import com.experise.course.springpizza.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {
}
